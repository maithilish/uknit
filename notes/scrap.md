
Intellij

1. try with resource 
2. var keyword https://developer.oracle.com/java/jdk-10-local-variable-type-inference.html
3. Switch case

        String result = "";
        switch (day) {
            case "M":
            case "W":
            case "F": {
                result = "MWF";
                break;
            }
            case "T":
            case "TH":
            case "S": {
                result = "TTS";
                break;
            }
        };

        System.out.println("Old Switch Result:");
        System.out.println(result);
        
        v12

        String result = switch (day) {
            case "M", "W", "F" -> "MWF";
            case "T", "TH", "S" -> "TTS";
            default -> {
                if(day.isEmpty())
                    break "Please insert a valid day.";
                else
                    break "Looks like a Sunday.";
            }

        };

        System.out.println(result);        
        
4. new String syntax

## Binding Key without type params

InternalCallProcessor.java

String clzNameWithoutTypeParam = clzName.replaceAll("<.*>", "");
String regEx = String.format("%s<.*>", clzNameWithoutTypeParam);
key = key.replaceAll(regEx, clzNameWithoutTypeParam);


