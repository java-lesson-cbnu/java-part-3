package kr.easw.lesson3;

import java.util.Arrays;
import java.util.Random;

public class RollTheDice {
    private static int[] frequency = new int[6];

    private static Random RANDOM = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 600; i++) {
            if (RANDOM.nextDouble() < 0.1) {
                fillArray(RANDOM.nextDouble() * 720);
            } else {
                fillArray(RANDOM.nextDouble() * 360);
            }
        }
    }

    /**
     * 해당 메서드는 다음과 같은 역할을 가져야 합니다 :
     * 주어진 값을 60으로 나눈 후, 나온 값의 수만큼 해당 인덱스에 존재하는 배열 값을 1 증가시켜야 합니다.
     * 또한, 어떠한 경우에서도 주어진 기능을 구현했을 떄, 오류가 발생해서는 안됩니다.
     * <p>
     * 입력값은 일반적으로는 360을 넘지 않으나, 낮은 확률로 360을 넘습니다.
     * 이러한 경우, extendArray 메서드를 구현하여 이를 이용해 배열을 재선언해야 합니다.
     * <p>
     * 또한, 입력값이 double임으로 60으로 나눈 이후 int로 캐스팅이 필요합니다.
     */
    private static void fillArray(double result) {
        // 주사위의 눈금을 구하기 위해 60으로 나눈 후, int로 캐스팅해 소수점을 제거합니다.
        int dice = (int) (result / 60);
        // 현재 주사위의 눈금이 frequency 배열의 크기보다 큰지 비교합니다.
        // 값이 360일 경우에 60으로 나눌 경우, 인덱스가 6이 되어 7번째 자리를 요구하기 떄문에
        // >=를 사용하여 비교해야 합니다.
        if (dice >= frequency.length) {
            // 만약 대상 눈금이 배열 크기보다 크거나 같다면, 눈금의 크기만큼 늘린 배열로 frequency 배열을 교체합니다.
            frequency = extendArray(dice + 1);
        }
        // 해당 눈금이 나온 빈도를 1 증가시킵니다.
        frequency[dice]++;
    }

    /**
     * 해당 메서드는 다음과 같은 역할을 가져야 합니다 :
     * 주어진 값의 크기만큼 배열을 생성한 후, 기존 배열에 있던 데이터를 복사해 반환해야 합니다.
     */
    private static int[] extendArray(int next) {
        // 이전 강의에서 소개된 Arrays.copyOf를 통해 새 배열을 생성합니다.
        // 첫번째 변수로 frequency를 넣어 이 변수를 복사하겠다는것을 알리고,
        // next 파라미터로 이 길이까지 증가시키겠다는것을 나타냅니다.
        return Arrays.copyOf(frequency, next);
    }
}
