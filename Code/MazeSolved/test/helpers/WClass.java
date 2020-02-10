package helpers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract class WClass {

    protected String _className;
    protected Class _wrapC;
    protected Map<String, Constructor> _wrapCtors;
    protected Map<String, Method> _wrapMs;
    protected Object _wrapObj;
    
    @SuppressWarnings("deprecation")
    private void loadClass() {
        _wrapCtors = new HashMap<String, Constructor>();
        for (Constructor ctor : _wrapC.getConstructors()) {
            _wrapCtors.put(ctor.toString(), ctor);
        }
        _wrapMs = new HashMap<String, Method>();
        for (Method m : _wrapC.getMethods()) {
            _wrapMs.put(m.toString(), m);
        }
        for (Method m : _wrapC.getDeclaredMethods()) {
            if (!_wrapMs.containsKey(m.toString())) {
                _wrapMs.put(m.toString(), m);
                if (!m.isAccessible()) {
                    m.setAccessible(true);
                }
            }
        }
        for (Method m : _wrapC.getSuperclass().getDeclaredMethods()) {
            if (!_wrapMs.containsKey(m.toString())) {
                _wrapMs.put(m.toString(), m);
                if (!m.isAccessible()) {
                    m.setAccessible(true);
                }
            }
        }
    }
    
    //Region: Constructor/instantiate/newInstance sequence
    public WClass() {
        _className = this.getClass().getName().replace("helpers.", "");
        try {
            _wrapC = Class.forName(_className);
            loadClass();
            _wrapObj = null;
        } catch (Exception e) {
            fail(String.format("### Missing or invalid '%s' class definition.", _className));
        }
    }
    
    public WClass(Object o) {
        _className = o.getClass().getName();
        _wrapC = o.getClass();
        loadClass();
        _wrapObj = o;
    }
    
    protected abstract void instantiate() throws Exception;
    
    public WClass newInstance() {
        try {
            instantiate();
        } catch (Exception e) {
            fail(String.format("### Missing or invalid '%s' constructor.", _className));
        }
        return this;
    }
    //EndRegion: Constructor/instantiate/newInstance sequence
    
    public Object getInstance() {
        return _wrapObj;
    }
    
    protected Object invoke(String mName, Object... params) {
        Object result = null;
        Method mGetRow = getMethod(mName);
        try {
            result = mGetRow.invoke(_wrapObj, params);
        } catch (Exception e) {
            fail(String.format("### Runtime error invoking '%s'.", mGetRow.getName()));
        }
        return result;
    }
    
    public static void checkChain(String[] chain) {
        for (int i = 0; i < chain.length-1; i++) {
            Class c1 = null;
            try {
                c1 = Class.forName(chain[i]);
            } catch(ClassNotFoundException e) {
                fail(String.format("### Missing or invalid '%s' class definition." ,  chain[i]));
            }
            
            Class c2 = null;
            try {
                c2 = Class.forName(chain[i+1]);
            } catch(ClassNotFoundException e) {
                fail(String.format("### Missing or invalid '%s' class definition." ,  chain[i+1]));
            }
            
            assertTrue(
                    String.format("Invalid class hierarchy: '%s' is not a subclass of '%s'.",
                            chain[i], chain[i+1]),
                    c1.getSuperclass().equals(c2));
        }
    }
    
    private static boolean checkDeclMatch(String pattern, String decl) {
        String[] parts = pattern.split("\\*");
        boolean matched = true;
        int idx = 0;
        for (int i = 0; matched && i < parts.length; i++) {
            if (!parts[i].trim().isEmpty()) {
                idx = decl.indexOf(parts[i], idx);
                matched = (idx != -1);
            }
        }
        return matched;
    }
    
    private static void checkAndMark(String actMethod, Map<String, Boolean> expMethods) {
        for(String expMethod : expMethods.keySet()) {
            if (!expMethods.get(expMethod)) {
                if (checkDeclMatch(expMethod, actMethod)) {
                    expMethods.replace(expMethod, true);
                    break;
                }
            }
        }
    }
    
    public static void checkClass(String className, String... decls) {
        try {
            String[] chain = className.split(" extends ");
            checkChain(chain);
            Class thisC = Class.forName(chain[0]);
            Class superC = thisC.getSuperclass();
            
            if (decls.length > 0) {
                Map<String, Boolean> mMap = new HashMap<String, Boolean>();
                for (String d : decls) {
                    mMap.put(d, false);
                }

                for (Constructor c : thisC.getConstructors()) {
                    mMap.replace(c.toString(), true);
                }
                
                for (Method m : thisC.getDeclaredMethods()) {
                    checkAndMark(m.toString(), mMap);
                }
                
                for (Method m : superC.getDeclaredMethods()) {
                    checkAndMark(m.toString(), mMap);
                }
                
                for (Map.Entry<String, Boolean > kvp : mMap.entrySet()) {
                    assertTrue(
                            String.format("### Missing or invalid '%s' method declaration in class '%s' ###.", kvp.getKey(), className), 
                            kvp.getValue());
                }
            }
        } catch(ClassNotFoundException e) {
            fail(String.format("### Missing or invalid '%s' class definition." ,  className));
        }
    }
    
    public Constructor getCtor(String cDecl) {
        assertTrue(
                String.format("### Missing or invalid '%s' constructor declaration in class '%s'.", cDecl, _wrapC.getName()),
                _wrapCtors.containsKey(cDecl));
        return _wrapCtors.get(cDecl);
    }
    
    public Method getMethod(String mPattern) {
        String mDecl = null;
        for (String m : _wrapMs.keySet()) {
            if (checkDeclMatch(mPattern, m)) {
                mDecl = m;
                break;
            }
        }
        assertTrue(
                String.format("### Missing or invalid '%s' method declaration in class '%s'.", mDecl, _wrapC.getName()),
                mDecl != null);
        return _wrapMs.get(mDecl);
    }
}
