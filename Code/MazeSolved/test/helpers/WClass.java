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
    protected Constructor _wrapCtr;
    protected Map<String, Method> _wrapMs;
    protected Object _wrapObj;
    
    //Region: Constructor/instantiate/newInstance sequence
    public WClass() {
        _className = this.getClass().getName().replace("helpers.", "");
        try {
            _wrapC = Class.forName(_className);
            _wrapCtr = null;
            _wrapMs = new HashMap<String, Method>();
            for (Method m : _wrapC.getMethods()) {
                _wrapMs.put(m.toString(), m);
            }
            _wrapObj = null;
        } catch (Exception e) {
            fail(String.format("### Missing or invalid '%s' class definition.", _className));
        }
    }
    
    public WClass(Object o) {
        _className = o.getClass().getName();
        _wrapC = o.getClass();
        _wrapCtr = null;
        _wrapMs = new HashMap<String, Method>();
        for (Method m : _wrapC.getMethods()) {
            _wrapMs.put(m.toString(), m);
        }
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
    
    private static void checkAndMark(String actMethod, Map<String, Boolean> expMethods) {
        for(String expMethod : expMethods.keySet()) {
            if (!expMethods.get(expMethod)) {
                String[] expParts = expMethod.split("\\*");
                boolean matched = true;
                int idx = 0;
                for (int i = 0; matched && i < expParts.length; i++) {
                    if (!expParts[i].trim().isEmpty()) {
                        idx = actMethod.indexOf(expParts[i], idx);
                        matched = (idx != -1);
                    }
                }
                if (matched) {
                    expMethods.replace(expMethod, true);
                }
            }
        }
    }
    
    public static void checkClass(String className, String... decls) {
        try {
            String[] chain = className.split(" extends ");
            checkChain(chain);
            Class mazeC = Class.forName(chain[0]);
            Class superC = mazeC.getSuperclass();
            
            if (decls.length > 0) {
                Map<String, Boolean> mMap = new HashMap<String, Boolean>();
                for (String d : decls) {
                    mMap.put(d, false);
                }

                for (Constructor c : mazeC.getConstructors()) {
                    mMap.replace(c.toString(), true);
                }
                
                for (Method m : mazeC.getDeclaredMethods()) {
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
    
    public Method getMethod(String mDecl) {
        assertTrue(
                String.format("### Missing or invalid '%s' method declaration in class '%s'.", mDecl, _wrapC.getName()),
                _wrapMs.containsKey(mDecl));
        return _wrapMs.get(mDecl);
    }
}
