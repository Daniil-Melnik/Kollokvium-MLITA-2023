import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test {
    private static final int timeoutTime = 1;

    private static void find(String fName) throws InterruptedException {
        File file = new File("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc/tests/"+fName);
        if (file.exists()){
            long startTime = System.nanoTime();
            int n =0;
            boolean Alphabet[] = new boolean[26];
            boolean result = true;

            ArrayList<Character> ch_l = new ArrayList<>();

            for (int  i =0; i< 26; i++){
                Alphabet[i]=true;
            }
            ArrayList<String> DNF_str = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file)))
            {
                String line;
                while ((line = br.readLine()) != null) {
                    if(line.charAt(0)!='#'){
                        DNF_str.add(line+".");
                        n++;
                    }
                }
                //System.out.println(n);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i<n; i++){
                if (DNF_str.get(i).charAt(1) == '_'){
                    ch_l.add(DNF_str.get(i).charAt(0));
                }
            }

            for (int i = 0; i<n; i++){
                if (DNF_str.get(i).charAt(0) == '_'){
                    String edStr = DNF_str.get(i);
                    int m =1;
                    while(ch_l.contains(edStr.charAt(m))){
                        m++;
                    } 
                    Alphabet[edStr.charAt(m)-'a'] = false;
                }
            }

            boolean DNF[] = new boolean[n];
            for (int i =0; i<n; i++){
                DNF[i] = false;
            }
            for (int i =0; i<n; i++){
                String str = DNF_str.get(i);
                int k =0;
                while(str.charAt(k) != '_'){
                    DNF[i] = DNF[i] | Alphabet[str.charAt(k)-'a'];
                    //System.out.print(str.charAt(k));
                    k++;
                }
                k++;
                //System.out.println();
                while(str.charAt(k)!='.'){
                    DNF[i] = DNF[i] | !Alphabet[str.charAt(k)-'a'];
                    //System.out.print(str.charAt(k));
                    k++;
                    
                }
            }
            // for (int i =0; i<n; i++){
            //     //System.out.println(i+1 + " " + DNF[i]);
            // }
            for (int i =0; i<n; i++){
                result = result&DNF[i];
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000;  //divide by 1000000 to get milliseconds.

            System.out.println(fName +" "+ result+" "+duration);
        }
    }

    private static void findTimeout(String filename) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                find(filename);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        t.join(timeoutTime * 1000);
    }

    public static void main(String[] args) {
        File folder = new File("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc_java/tests");
        File[] files = folder.listFiles();

        List<Double> times = new ArrayList<>();

        // Measure the execution time for each file
        for (File file : files) {
            long startTime = System.nanoTime();

            try {
                findTimeout(file.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1_000_000_000.0;
            times.add(duration);
        }

        // Print the measured times in a single line
        StringBuilder output = new StringBuilder();
        for (double time : times) {
            if (time < timeoutTime) {
                output.append(time).append(" ");
            } else {
                output.append("inf ");
            }
        }
        output.deleteCharAt(output.length() - 1); // Remove the trailing space

        System.out.println(output);
        System.exit(0);
    }
}
