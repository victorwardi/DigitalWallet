import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Victor Wardi - @vwardi on 25/02/2019
 **/
public class WorkHours {


    public static List<String> findSchedules(int workHours, int dayHours, String pattern) {
        List<String> schedules = new ArrayList<>();




       /*


       pattern: 024???6
       workHours: 20
       daysHours: 6

       scheduledHours = 12

       availableWorkHours = workHours - scheduledHours = 20 - 12 = 8;

       availabeDays ??? = 3;

       possibleSchedules :

                           620
                           621
                           611
                           602
                           530
                           521
                           512
                           503
                           440
                           431
                           422
                           413
                           404
                           350
                           341
                           332
                           323
                           314
                           260
                           251
                           242
                           233
                           224
                           215
                           206
                           116
                           125
                           134
                           143
                           152
                           161


        */


        System.out.println("Pattern: " + pattern);
        int availableDays = getAvaiableDays(pattern);
        System.out.println("Available days: " + availableDays);

        int scheduledHours = getScheduledHours(workHours, pattern);
        System.out.println("Scheduled hours: " + scheduledHours);

        int availableHours = workHours - scheduledHours;
        System.out.println("Available hours to work: " + availableHours);

        System.out.println("Max hours of work in a day: " + dayHours);

        int maxFullDays = availableHours / dayHours;

        System.out.println("Max Full days: " + maxFullDays);

        //   int fullDays = availableHours / dayHours;

        int restHours = availableHours % dayHours;

        System.out.println("Rest hours : " + restHours);

        int mininumHoursDay = (availableHours - dayHours) / availableDays;

        System.out.println("Minimum hours of work in a day: " + mininumHoursDay);

        for (int k = 0, j = availableHours; k <= availableDays; k++, j--) {
            System.out.println(k + "" + j + " : total = " + (j + k));
            System.out.println(j + "" + k + " : total = " + (j + k));
        }


        for (int i = 0; i < availableDays; i++) {

            String schedule = "";

            if (availableHours > 0 && availableHours < dayHours) {
                schedule += availableHours;
                availableHours = availableHours - dayHours;
                // System.out.print(schedule);

            } else {


                schedule += availableHours < 0 ? 0 : availableHours;


                //System.out.print(schedule);
            }


        }


        return schedules;

    }


    private static Set<String> permutations(int maxHoursDay, int availabeDays, int totalHours) {
        Set<String> permutations = new HashSet<>();


        for (int j = 0; j <= maxHoursDay; j++)
            for (int i = 0; i <= maxHoursDay; i++)
                for (int k = 0; k <= maxHoursDay; k++) {
                    if ((i + j + k) == maxHoursDay) {
                        // System.out.println(i + "" + j + "" + k);
                        permutations.add(i + "" + j + "" + k);
                    }
                }


        return permutations;

    }

    static String maxWorkHourDay(int maxHoursDay, int availabeDays) {


            String s = "";
        if (availabeDays != 0) {
            return s + maxWorkHourDay(maxHoursDay , availabeDays - 1);

            }

           return s;
    }

    private static int factorial(int number) {

        if (number == 1) {
            return number;
        } else {
            return factorial(number - 1) * number;
        }


    }

    /**
     * permutation function
     * @param str string to calculate permutation for
     * @param l starting index
     * @param r end index
     */
    private static void permute(String str, int l, int r)
    {
        if (l == r)
            System.out.println(str);
        else
        {
            for (int i = l; i <= r; i++)
            {
                str = swap(str,l,i);
                permute(str, l+1, r);
                str = swap(str,l,i);
            }
        }
    }

    /**
     * Swap Characters at position
     * @param a string value
     * @param i position 1
     * @param j position 2
     * @return swapped string
     */
    public static String swap(String a, int i, int j)
    {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i] ;
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }




    static int getAvaiableDays(String pattern) {
        return pattern.split("\\?", -1).length - 1;
    }

    static int getScheduledHours(int workHours, String pattern) {
        return Pattern.compile("")
                .splitAsStream(pattern.replace("?", ""))
                .mapToInt(Integer::parseInt)
                .sum();
    }


    public static void main(String[] args) {


            int maxWorkHours = 8;

           // int[] schedule = new int[3];

            Set<int[]> schedules = new HashSet<>();

            double combinations = Math.pow(8, 3);

        for (int c = 0; c < combinations; c++) {

            int[] schedule = new int[3];

            for (int i = 0; i <= maxWorkHours; i++) {

                schedule[0] = i;

                for (int j = 0; j <= maxWorkHours; j++) {

                    schedule[1] = j;

                    for (int k = 0; k <= maxWorkHours; k++) {

                        schedule[2] = k;

                        int[] scheduleTemp = { schedule[0],  schedule[1],  schedule[2]};

                        if(isScheculeValid(scheduleTemp, 12)){
                            schedules.add(scheduleTemp);
                        }
                    }
                }
            }



        }


        for ( int[] s : schedules ) {

            String st = "";
            for (int i = 0; i < s.length; i++) {
                st += s[i];

            }
            System.out.println(st);

        }


        }

    private static boolean isScheculeValid(int[] schedule, int maxWorkHours) {

        int total = 0;
        for (int h : schedule ) {
            total += h;
        }
        return total == maxWorkHours ? true : false;
    }
}

