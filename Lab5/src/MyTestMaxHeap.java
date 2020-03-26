
public class MyTestMaxHeap {
    public static void main(String[] args) {

        MaxHeap test = new MaxHeap(1);
        Integer[] nums = {1, 2, 3, 0, 5, 50, -200, 25, 1, 59, -50, 123, 80, 20, 1, 3, 5, 7, -100};
        Integer[] nums2 = {-1, 0, 1, 2, 100, 1000, 10000};
        Integer[][] numsList = {
            {9, 8, 7, 6, 5, 4, 3, 2, 1},
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {-5, 5, -4, 4, -3, 3, -2 ,2, -1, 1, 0},
            {},
            {1},
            nums.clone(),
        };
        
        System.out.println("<INSERT TEST/>");
        for(Integer num : nums) {
            test.insert(num);
            System.out.format(".insert(%d):\t%s\n", num, test);
            System.out.format("size, capacity:\t%d, %d\n"
                    ,test.getSize()
                    ,test.getCapacity());
        } System.out.println("</INSERT TEST>\n");
        
        System.out.println("<DELETE MAX TEST/>");
        for(int i=test.getSize(); i>0; i--) {
            test.deleteMax();
            System.out.format(".deleteMax():\t%s\n", test);
            System.out.format("size, capacity:\t%d, %d\n"
                    ,test.getSize()
                    ,test.getCapacity());
        } System.out.println("</DELETE MAX TEST>\n");
        
        System.out.println("<CONSTRUCTOR 1 TEST/>");
        for(Integer num : nums2) {
            try {
                MaxHeap test2 = new MaxHeap(num);
                System.out.format("MaxHeap(%d):\t%s\n", num, test2);
                System.out.format("size, capacity:\t%d, %d\n"
                        ,test2.getSize()
                        ,test2.getCapacity());
            } catch(Exception e) {
                System.out.format("MaxHeap(%d): \n", num);
                System.out.println(e);
            }
        } System.out.println("</CONSTRUCTOR 1 TEST>\n");
        
        System.out.println("<CONSTRUCTOR 2 TEST/>");
        for(Integer arr[] : numsList) {
            try {
                MaxHeap test2 = new MaxHeap(arr);
                String input = "";
                for(Integer num:arr) input += num + " ";
                System.out.format("array: %s\n", input);
                System.out.format("MaxHeap(array):\t%s\n", test2);
                System.out.format("size, capacity:\t%d, %d\n"
                        ,test2.getSize()
                        ,test2.getCapacity());
            } catch(Exception e) {
                String input = "";
                for(Integer num:arr) input += num + " ";
                System.out.format("array: %s\n", input);
                System.out.println(e);
            }
        } System.out.println("</CONSTRUCTOR 2 TEST>\n");
        
        System.out.println("<HEAP SORT TEST/>");
        for(Integer arr[] : numsList) {
            Integer[] sort = arr.clone();
            String before = "";
            for(Integer num:sort) before += num + " ";
            MaxHeap.heapSort(sort);
            String after = "";
            for(Integer num:sort) after += num + " ";
            System.out.format("before sorting:\t %s\n", before);
            System.out.format("after sorting:\t %s\n", after);
        } System.out.println("</HEAP SORT TEST>\n");
        
    }
}