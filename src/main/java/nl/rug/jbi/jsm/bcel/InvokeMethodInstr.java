package nl.rug.jbi.jsm.bcel;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.Type;

import java.util.Arrays;
import java.util.List;

import static nl.rug.jbi.jsm.bcel.BCELTools.type2className;

/**
 * Represents the invocation of a method.
 *
 * @author David van Leusen
 * @since 1.0
 */
public class InvokeMethodInstr {
    private final InvokeInstruction instruction;
    private final ConstantPoolGen cp;

    public InvokeMethodInstr(final InvokeInstruction instruction, final ConstantPoolGen cp) {
        this.instruction = instruction;
        this.cp = cp;
    }

    /**
     * @return List of Strings representing the types of the arguments of the invoked method.
     */
    public List<String> getArgumentTypes() {
        return Lists.transform(Arrays.asList(this.instruction.getArgumentTypes(this.cp)), new Function<Type, String>() {
            @Override
            public String apply(final Type type) {
                return type2className(type);
            }
        });
    }

    /**
     * @return String representing the type of the object this method returns.
     */
    public String getReturnType() {
        return type2className(this.instruction.getReturnType(this.cp));
    }

    /**
     * @return String representing the class the invoked method belongs to.
     */
    public String getClassName() {
        return type2className(this.instruction.getReferenceType(this.cp));
    }

    /**
     * @return Name of the invoked method.
     */
    public String getMethodName() {
        return this.instruction.getMethodName(this.cp);
    }
}
