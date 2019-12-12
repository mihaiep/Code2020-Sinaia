package helpers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MazeTest {
    
    public static Method getMethod(String cName, String mDecl, Map<String, Method> mMap) {
        assertTrue(
                String.format("### Missing or invalid '%s' method declaration in class '%s'.", mDecl, cName),
                mMap.containsKey(mDecl));
        return mMap.get(mDecl);
    }

    @SuppressWarnings("rawtypes")
    public static void checkClass(String className, String... decls) {
        try {
            Class mazeC = Class.forName(className);
            
            if (decls.length > 0) {
                Map<String, Boolean> mMap = new HashMap<String, Boolean>();
                for (String d : decls) {
                    mMap.put(d, false);
                }

                for (Constructor c : mazeC.getConstructors()) {
                    mMap.replace(c.toString(), true);
                }
                
                for (Method m : mazeC.getDeclaredMethods()) {
                    mMap.replace(m.toString(),  true);
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
}
