/******************************************************************
 *
 *   Evey Kallmeyer / COMP 272/400D-002
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ********************************************************************/

import java.util.Arrays;

public class ProblemSolutions {

    /**
     * Method SelectionSort
     *
     * This method performs a selection sort. This file will be spot checked,
     * so ENSURE you are performing a Selection Sort!
     *
     * This is an in-place sorting operation that has two function signatures. This
     * allows the second parameter to be optional, and if not provided, defaults to an
     * ascending sort. If the second parameter is provided and is false, a descending
     * sort is performed.
     *
     * @param values        - int[] array to be sorted.
     * @param ascending     - if true,method performs an ascending sort, else descending.
     *                        There are two method signatures allowing this parameter
     *                        to not be passed and defaulting to 'true (or ascending sort).
     */

    public  void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending ) {

        int n = values.length;

        for (int i = 0; i < n - 1; i++) {

            // define the index of the first element of the unsorted array, aka the index that will be swapped with j
            int selectionIndex = i;

            // loop through the unsorted portion of the array to find the next element to be moved
            for (int j = i; j < n; j++) {
                // find min for ascending
                if (ascending) {
                    if (values[j] < values[selectionIndex]) {
                        selectionIndex = j;
                    }
                // find max for descending
                } else {
                    if (values[j] > values[selectionIndex]) {
                        selectionIndex = j;
                    }
                }
            }

            // swap selectionIndex i with j
            int temp = values[i];
            values[i] = values[selectionIndex];
            values[selectionIndex] = temp;

        }

    } // End class selectionSort


    /**
     *  Method mergeSortDivisibleByKFirst
     *
     *  This method will perform a merge sort algorithm. However, all numbers
     *  that are divisible by the argument 'k', are returned first in the sorted
     *  list. Example:
     *        values = { 10, 3, 25, 8, 6 }, k = 5
     *        Sorted result should be --> { 10, 25, 3, 6, 8 }
     *
     *        values = { 30, 45, 22, 9, 18, 39, 6, 12 }, k = 6
     *        Sorted result should be --> { 30, 18, 6, 12, 9, 22, 39, 45 }
     *
     * As shown above, this is a normal merge sort operation, except for the numbers
     * divisible by 'k' are first in the sequence.
     *
     * @param values    - input array to sort per definition above
     * @param k         - value k, such that all numbers divisible by this value are first
     */

    public void mergeSortDivisibleByKFirst(int[] values, int k) {

        // Protect against bad input values
        if (k == 0)  return;
        if (values.length <= 1)  return;

        mergeSortDivisibleByKFirst(values, k, 0, values.length-1);
    }

    private void mergeSortDivisibleByKFirst(int[] values, int k, int left, int right) {

        if (left >= right)
            return;

        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(values, k, left, mid);
        mergeSortDivisibleByKFirst(values, k, mid + 1, right);
        mergeDivisbleByKFirst(values, k, left, mid, right);
    }

    /*
     * The merging portion of the merge sort, divisible by k first
     */

    private void mergeDivisbleByKFirst(int arr[], int k, int left, int mid, int right) {
        
        // find the size of each side of the array
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        // create temporary arrays for each side of the original array
        int[] leftArr = new int[leftSize];
        int[] rightArr = new int[rightSize];
        for (int i = 0; i < leftSize; i++) {
            leftArr[i] = arr[left+ i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        // set indexes for each array
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = left;

        // merge arrays (elements divisible by k at the front, ascending order for nondivisible elements after)
        while (leftIndex < leftSize && rightIndex < rightSize) {

            boolean leftDivisible = (leftArr[leftIndex] % k == 0);
            boolean rightDivisible = (rightArr[rightIndex] % k == 0);
            
            // if the left element is divisble by k, and the right is not, put the left element first
            if (leftDivisible && !rightDivisible) {
                arr[mergedIndex++] = leftArr[leftIndex++];
            }
            // if the right element is divisble by k, and the left is not, put the right element first
            else if (!leftDivisible && rightDivisible) {
                arr[mergedIndex++] = rightArr[rightIndex++];
            }
            // if neither element is divisible, merge-sort in ascending order
            else if (!leftDivisible && !rightDivisible) {
                if (leftArr[leftIndex] <= rightArr[rightIndex]) {
                    arr[mergedIndex++] = leftArr[leftIndex++];
                } else {
                    arr[mergedIndex++] = rightArr[rightIndex++];
                }
            }
            // if both the left and right are divisble by k, put them both into the new array in the current order they are already in
            else {
                arr[mergedIndex++] = leftArr[leftIndex++];
            }
        }

        // merge the rest of the left side, if the right side is empty
        while (leftIndex < leftSize) {
            arr[mergedIndex ++] = leftArr[leftIndex++];
        }

        // merge the rest of the right side, if the left side is empty
        while (rightIndex < rightSize) {
            arr[mergedIndex ++] = rightArr[rightIndex++];
        }
    }


    /**
     * Method asteroidsDestroyed
     *
     * You are given an integer 'mass', which represents the original mass of a planet.
     * You are further given an integer array 'asteroids', where asteroids[i] is the mass
     * of the ith asteroid.
     *
     * You can arrange for the planet to collide with the asteroids in any arbitrary order.
     * If the mass of the planet is greater than or equal to the mass of the asteroid, the
     * asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the
     * planet is destroyed.
     *
     * Return true if possible for all asteroids to be destroyed. Otherwise, return false.
     *
     * Example 1:
     *   Input: mass = 10, asteroids = [3,9,19,5,21]
     *   Output: true
     *
     * Explanation: One way to order the asteroids is [9,19,5,3,21]:
     * - The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
     * - The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
     * - The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
     * - The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
     * - The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
     * All asteroids are destroyed.
     *
     * Example 2:
     *   Input: mass = 5, asteroids = [4,9,23,4]
     *   Output: false
     *
     * Explanation:
     * The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
     * After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
     * This is less than 23, so a collision would not destroy the last asteroid.
     *
     * Constraints:
     *     1 <= mass <= 105
     *     1 <= asteroids.length <= 105
     *     1 <= asteroids[i] <= 105
     *
     * @param mass          - integer value representing the mass of the planet
     * @param asteroids     - integer array of the mass of asteroids
     * @return              - return true if all asteroids destroyed, else false.
     */

    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {

        // sort asteroids in ascedning order so we face the largest mass last, giving a hgher probability of defeating them all
        Arrays.sort(asteroids);
        
        // try to destroy each asteroid
        long currentMass = mass;
        for (int asteroid : asteroids) {
            // return false if an asteroid is not destroyed
            if (currentMass < asteroid) {
                return false;
            }
            // add the asteroid's mass to the mass of the planet
            currentMass += asteroid;
        }

        // return true if all asteroids were destroyed
        return true;
    }


    /**
     * Method numRescueSleds
     *
     * You are given an array people where people[i] is the weight of the ith person,
     * and an infinite number of rescue sleds where each sled can carry a maximum weight
     * of limit. Each sled carries at most two people at the same time, provided the
     * sum of the weight of those people is at most limit. Return the minimum number
     * of rescue sleds to carry every given person.
     *
     * Example 1:
     *    Input: people = [1,2], limit = 3
     *    Output: 1
     *    Explanation: 1 sled (1, 2)
     *
     * Example 2:
     *    Input: people = [3,2,2,1], limit = 3
     *    Output: 3
     *    Explanation: 3 sleds (1, 2), (2) and (3)
     *
     * Example 3:
     *    Input: people = [3,5,3,4], limit = 5
     *    Output: 4
     *    Explanation: 4 sleds (3), (3), (4), (5)
     *
     * @param people    - an array of weights for people that need to go in a sled
     * @param limit     - the weight limit per sled
     * @return          - the minimum number of rescue sleds required to hold all people
     */

    public static int numRescueSleds(int[] people, int limit) {

        // sort people by weight in ascending order
        Arrays.sort(people);

        // assign pointers for lightest and heaviest people
        int left = 0;
        int right = people.length - 1;

        // count how many sleds are needed
        int sleds = 0;

        // assign people to sleds
        while (left <= right) {
            // try to place the lightest and heaviest people together
            if (people[left] + people[right] <= limit) {
                // move left pointer if successful
                left++;
            }
            // the heaviest person is assigned a sled, either by themself, or with the lightest person, so move the right pointer
            right--;
            
            // increase the sled count
            sleds++;
        }
        
        // return the number of sleds used
        return sleds;
    }

} // End Class ProblemSolutions

