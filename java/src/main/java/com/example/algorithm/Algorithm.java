package com.example.algorithm;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class Algorithm {
    public static void main(String[] args) {
        int[] arrays = {23, 43, 54, 82, 12, 94, 27, 53, 87, 26, 12};
//        bubbleSort(arrays);
//        selectSort(arrays);
//        insertSort(arrays);
//        dichotomyQueryResult(arrays, 27);
        System.out.println(Arrays.toString(arrays));
    }

    public static void printArray(int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + (i == arrays.length - 1 ? "\n" : ", "));
        }
    }

    /**
     * 循环n-1次，当次循环起始元素和后面元素比较，较大的后移
     *
     * @param arrays
     */
    public static void bubbleSort(int[] arrays) {
        for (int i = arrays.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arrays[j] > arrays[j + 1]) {
                    int temp = arrays[j];
                    arrays[j] = arrays[j + 1];
                    arrays[j + 1] = temp;
                }
            }
        }
        System.out.println("bubbleSort--result: ");
        printArray(arrays);
    }

    /**
     * 循环n-1次，每次查找最小元素下标，交换最小元素下标和当次循环起始元素
     *
     * @param arrays
     */
    public static void selectSort(int[] arrays) {
        for (int i = 0; i < arrays.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arrays.length; j++) {
                if (arrays[min] > arrays[j])
                    min = j;
            }
            if (i != min) {
                int temp = arrays[min];
                arrays[min] = arrays[i];
                arrays[i] = temp;
            }
        }
        System.out.println("selectSort--result: ");
        printArray(arrays);
    }

    /**
     * 循环n-1次，当次循环是一个有序数组，大于插入元素的数组元素后移，直到等于或小于的元素空位插入
     *
     * @param arrays
     */
    public static void insertSort(int[] arrays) {
        for (int i = 1; i < arrays.length; i++) {
            int insert = arrays[i];
//            for (int j = i - 1; j >= 0; j--) {
//                arrays[j + 1] = arrays[j] > insert ? arrays[j] : insert;
//                if (arrays[j] <= insert)
//                    break;
//            }
            int j = i - 1;
            while (j >= 0 && arrays[j] > insert) {
                arrays[j + 1] = arrays[j];
                j--;
            }
            arrays[j + 1] = insert;
        }
        System.out.println("insertSort--result: ");
        printArray(arrays);
    }

    public static void dichotomyQueryResult(int[] arrays, int target) {
        int index = dichotomyQuery(arrays, target);
        if (index == -1) {
            System.out.println("数组不包含元素: " + target);
        } else {
            System.out.println("元素: " + target + "在数组下标为: " + index);
        }
    }

    public static int dichotomyQuery(int[] arrays, int target) {
        int start = 0;
        int end = arrays.length - 1;
        int mid = (start + end) / 2;
        while (start <= end) {
            if (arrays[mid] < target) {
                start = mid + 1;
            } else if (arrays[mid] > target) {
                end = mid - 1;
            } else {
                return mid;
            }
            mid = (start + end) / 2;
        }
        return -1;
    }
}
