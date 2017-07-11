package com.luxoft.instrument.text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterizedMessage implements Message {
	final static Logger LOG = LoggerFactory.getLogger(ParameterizedMessage.class);
	
	private static final char DELIM_START = '{';
    private static final char DELIM_STOP = '}';
    private static final char ESCAPE_CHAR = '\\';
    public static final String RECURSION_PREFIX = "[...";    
    public static final String RECURSION_SUFFIX = "...]";
    
    private final String messagePattern;   
    private transient Object[] argArray;
    private transient String formattedMessage;
    
    public ParameterizedMessage() {
        this("", new Object[]{});
    }      
    
    public ParameterizedMessage(final String messagePattern, Object... objectArgs) {
        this.messagePattern = messagePattern;        
        this.argArray = argumentsToStrings(objectArgs);
    }
          
    public ParameterizedMessage(final String messagePattern, final Object arg) {
        this(messagePattern, new Object[]{arg});
    }
    
    public ParameterizedMessage(final String messagePattern, final Object arg1, final Object arg2) {
        this(messagePattern, new Object[]{arg1, arg2});
    }
    
    private String[] argumentsToStrings(final Object[] arguments) {
        if (arguments == null) {
            return null;
        }
        final int argsCount = countArgumentPlaceholders(messagePattern);
        int resultArgCount = arguments.length;        
        argArray = new Object[resultArgCount];
        System.arraycopy(arguments, 0, argArray, 0, resultArgCount);

        String[] strArgs;
        if (argsCount == 1 && arguments.length > 1) {
            // special case
            strArgs = new String[1];
            strArgs[0] = deepToString(arguments);
        } else {
            strArgs = new String[resultArgCount];
            for (int i = 0; i < strArgs.length; i++) {
                strArgs[i] = deepToString(arguments[i]);
            }
        }
        return strArgs;
    }
    
    public String getFormattedMessage(final String messagePattern, Object... arguments) {
    	argArray = arguments;       
        formattedMessage = formatMessage(messagePattern, argArray);        
        return formattedMessage;
    }
    
    public String getFormattedMessage() {       
        formattedMessage = formatMessage(messagePattern, argArray);        
        return formattedMessage;
    }
    
    public String getFormat() {
        return messagePattern;
    }
    
    public Object[] getParameters() {
    	return argArray;        
    }
    
    protected String formatMessage(final String msgPattern, Object... arguments) {
        return format(msgPattern, arguments);
    }
    
    public static String format(final String messagePattern, Object... arguments) {
        if (messagePattern == null || arguments == null || arguments.length == 0) {
            return messagePattern;
        }

        final StringBuilder result = new StringBuilder();
        int escapeCounter = 0;
        int currentArgument = 0;
        for (int i = 0; i < messagePattern.length(); i++) {
            final char curChar = messagePattern.charAt(i);
            if (curChar == ESCAPE_CHAR) {
                escapeCounter++;
            } else {
                if (curChar == DELIM_START && i < messagePattern.length() - 1
                        && messagePattern.charAt(i + 1) == DELIM_STOP) {
                    // write escaped escape chars
                    final int escapedEscapes = escapeCounter / 2;
                    for (int j = 0; j < escapedEscapes; j++) {
                        result.append(ESCAPE_CHAR);
                    }

                    if (escapeCounter % 2 == 1) {
                        // i.e. escaped
                        // write escaped escape chars
                        result.append(DELIM_START);
                        result.append(DELIM_STOP);
                    } else {
                        // unescaped
                        if (currentArgument < arguments.length) {
                            result.append(arguments[currentArgument]);
                        } else {
                            result.append(DELIM_START).append(DELIM_STOP);
                        }
                        currentArgument++;
                    }
                    i++;
                    escapeCounter = 0;
                    continue;
                }
                // any other char beside ESCAPE or DELIM_START/STOP-combo
                // write unescaped escape chars
                if (escapeCounter > 0) {
                    for (int j = 0; j < escapeCounter; j++) {
                        result.append(ESCAPE_CHAR);
                    }
                    escapeCounter = 0;
                }
                result.append(curChar);
            }
        }
        return result.toString();
    }
    
    public static int countArgumentPlaceholders(final String messagePattern) {
        if (messagePattern == null) {
            return 0;
        }
        final int delim = messagePattern.indexOf(DELIM_START);
        if (delim == -1) {
            // special case, no placeholders at all.
            return 0;
        }
        int result = 0;
        boolean isEscaped = false;
        for (int i = 0; i < messagePattern.length(); i++) {
            final char curChar = messagePattern.charAt(i);
            if (curChar == ESCAPE_CHAR) {
                isEscaped = !isEscaped;
            } else if (curChar == DELIM_START) {
                if (!isEscaped && i < messagePattern.length() - 1 && messagePattern.charAt(i + 1) == DELIM_STOP) {
                    result++;
                    i++;
                }
                isEscaped = false;
            } else {
                isEscaped = false;
            }
        }
        return result;
    }

    public static String deepToString(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return (String) o;
        }
        final StringBuilder str = new StringBuilder();
        final Set<String> dejaVu = new HashSet<String>(); // that's actually a neat name ;)
        recursiveDeepToString(o, str, dejaVu);
        return str.toString();
    }
    
    private static void recursiveDeepToString(final Object o, final StringBuilder str, final Set<String> dejaVu) {
        if (o == null) {
            str.append("null");
            return;
        }
        if (o instanceof String) {
            str.append(o);
            return;
        }
        final Class<?> oClass = o.getClass();
        if (oClass.isArray()) {
            if (oClass == byte[].class) {
                str.append(Arrays.toString((byte[]) o));
            } else if (oClass == short[].class) {
                str.append(Arrays.toString((short[]) o));
            } else if (oClass == int[].class) {
                str.append(Arrays.toString((int[]) o));
            } else if (oClass == long[].class) {
                str.append(Arrays.toString((long[]) o));
            } else if (oClass == float[].class) {
                str.append(Arrays.toString((float[]) o));
            } else if (oClass == double[].class) {
                str.append(Arrays.toString((double[]) o));
            } else if (oClass == boolean[].class) {
                str.append(Arrays.toString((boolean[]) o));
            } else if (oClass == char[].class) {
                str.append(Arrays.toString((char[]) o));
            } else {
                // special handling of container Object[]
                final String id = identityToString(o);
                if (dejaVu.contains(id)) {
                    str.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
                } else {
                    dejaVu.add(id);
                    final Object[] oArray = (Object[]) o;
                    str.append('[');
                    boolean first = true;
                    for (final Object current : oArray) {
                        if (first) {
                            first = false;
                        } else {
                            str.append(", ");
                        }
                        recursiveDeepToString(current, str, new HashSet<String>(dejaVu));
                    }
                    str.append(']');
                }                
            }
        } else if (o instanceof Map) {
            // special handling of container Map
            final String id = identityToString(o);
            if (dejaVu.contains(id)) {
                str.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
            } else {
                dejaVu.add(id);
                final Map<?, ?> oMap = (Map<?, ?>) o;
                str.append('{');
                boolean isFirst = true;
                for (final Object o1 : oMap.entrySet()) {
                    final Map.Entry<?, ?> current = (Map.Entry<?, ?>) o1;
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        str.append(", ");
                    }
                    final Object key = current.getKey();
                    final Object value = current.getValue();
                    recursiveDeepToString(key, str, new HashSet<String>(dejaVu));
                    str.append('=');
                    recursiveDeepToString(value, str, new HashSet<String>(dejaVu));
                }
                str.append('}');
            }
        } else if (o instanceof Collection) {
            // special handling of container Collection
            final String id = identityToString(o);
            if (dejaVu.contains(id)) {
                str.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
            } else {
                dejaVu.add(id);
                final Collection<?> oCol = (Collection<?>) o;
                str.append('[');
                boolean isFirst = true;
                for (final Object anOCol : oCol) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        str.append(", ");
                    }
                    recursiveDeepToString(anOCol, str, new HashSet<String>(dejaVu));
                }
                str.append(']');
            }
        } else if (o instanceof Date) {
            final Date date = (Date) o;
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            // I'll leave it like this for the moment... this could probably be optimized using ThreadLocal...
            str.append(format.format(date));
        } else {
            // it's just some other Object, we can only use toString().
            try {
                str.append(o.toString());
            } catch (Exception e) {
            	LOG.warn("An exception ocurred while invoking the toString from {}", o);
            	str.append(new StringBuilder().append(o).toString());
            }
        }
    }
   
    public static String identityToString(final Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(obj));
    }

    @Override
    public String toString() {
        return "ParameterizedMessage[messagePattern=" + messagePattern + ", argArray=" +
            Arrays.toString(argArray) + "]";
    }
}

