package kr.easw.lesson3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SnakeGameWithoutTails {

    private static final int BOARD_SIZE = 10;
    // 0 - 빈 타일
    // 1 - 스네이크 블럭
    // 2 - 아이템
    private static final int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

    private static final Random RANDOM = new Random();

    private static int score = 0;

    private static SnakeLocation location = new SnakeLocation(0, 0);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.print("[우측 (r) | 좌측 (l) | 위 (u) | 아래 (d) | 종료 (0) ] : ");
            if (!nextDirection(scanner.next())) {
                System.out.println("게임 오버!");
                System.out.printf("점수: %d\n", score);
                break;
            }
            if (!hasItemOnBoard())
                placeRandomItem();
        }
    }

    /**
     * 해당 메서드는 다음과 같은 역할을 가져야 합니다 :
     * 사용자의 입력을 받고, 다음 위치로 옮기거나 게임을 종료해야 합니다.
     * <p>
     * 허용되는 입력은 다음과 같습니다 :
     * - 우측(r) | 좌측 (l) | 위 (u) | 아래 (d) | 종료 (0)
     * <p>
     * 다음 좌표는 location 변수에 계속해서 업데이트되어야 합니다.
     * 만약 다음 좌표에 아이템이 존재한다면, 점수를 1 증가하고 다음 좌표의 값을 0으로 되돌려야 합니다.
     * <p>
     * 만약 값이 최대 값 (BOARD_SIZE)이상이 되거나 최소 값(0) 아래로 내려간다면 같은 좌표로 설정하여 이동하지 않도록 해야합니다.
     * <p>
     * 만약 사용자의 입력이 종료(0)였다면, false값을 반환하여 게임을 종료해야 합니다.
     */
    private static boolean nextDirection(String keyword) {
        // switch문을 통해 방향을 지정합니다.
        // 현재 보드에서는 x축과 y축이 반전되어있음으로, 이를 감안하여 이동해야 합니다.
        switch (keyword) {
            case "r":
                // 현재 가로축은 y축입니다.
                // 오른쪽으로 이동하기 위해 y 값을 1 증가시킵니다.
                location = new SnakeLocation(location.x, location.y + 1);
                break;
            case "l":
                // 현재 가로축은 y축입니다.
                // 왼쪽으로 이동하기 위해 y 값을 1 감소시킵니다.
                location = new SnakeLocation(location.x, location.y - 1);
                break;
            case "u":
                // 현재 세로축은 x축입니다.
                // 위쪽으로 이동하기 위해 x값을 1 감소시킵니다.
                // 보드의 끝은 10(BOARD_SIZE)이고, 0부터 보드를 출력하기 때문에 위쪽은 좌표 감소를 통해 이동하게 됩니다.
                location = new SnakeLocation(location.x - 1, location.y);
                break;
            case "d":
                // 현재 세로축은 x축입니다.
                // 위쪽으로 이동하기 위해 x값을 1 증가시킵니다.
                // 보드의 끝은 10(BOARD_SIZE)이고, 0부터 보드를 출력하기 때문에 아래쪽은 좌표 증가를 통해 이동하게 됩니다.
                location = new SnakeLocation(location.x + 1, location.y);
                break;
            case "0":
                // 0이 입력되었다면 게임 처리를 중단하고 종료시키기 위해 즉시 false를 반환합니다.
                return false;
        }
        // switch문을 벗어나 바깥에서 이러한 필터나 점수 처리를 하는것으로 4곳에서 사용되야 할 코드를 한곳에서 사용하기 떄문에
        // 전체적인 코드 줄 수를 줄일 수 있습니다.

        // 최소와 최대치를 필터링합니다.
        if (location.x < 0) {
            location = new SnakeLocation(0, location.y);
        }
        if (location.y < 0) {
            location = new SnakeLocation(location.x, 0);
        }
        if (location.x >= BOARD_SIZE) {
            location = new SnakeLocation(BOARD_SIZE - 1, location.y);
        }
        if (location.y >= BOARD_SIZE) {
            location = new SnakeLocation(location.x, BOARD_SIZE - 1);
        }

        // 현재 있는 위치가 아이템 위치인지 확인합니다.
        if (board[location.x][location.y] == 2) {
            // 아이템이라면, 점수를 1 증가시킵니다.
            score++;
            // 또한, 아이템을 없애 중복된 점수 처리를 막습니다.
            board[location.x][location.y] = 0;
        }
        return true;
    }

    // 인코딩 문제로 인한 코드 깨짐 문제로 임시로 캐릭터 위치를 x, 빈 곳을 y, 아이템을 *으로 변경하였습니다.
    private static void printBoard() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (location.getX() == x && location.getY() == y) {
                    System.out.print("x ");
                    continue;
                }
                switch (board[x][y]) {
                    case 0:
                        System.out.print("y ");
                        break;
                    case 1:
                        System.out.print("x ");
                        break;
                    case 2:
                        System.out.print("* ");
                        break;
                }
            }
            System.out.println();
        }
    }

    private static void placeRandomItem() {
        // for문의 조건으로 랜덤을 넣을 경우, 계속 비교하여 최종 결과값이 바뀌므로 변수로 선언합니다.
        int toPlace = (int) (RANDOM.nextDouble() * 10);
        for (int i = 0; i < toPlace; i++) {
            int retry = 0;
            while (retry < 5) {
                SnakeLocation locate = new SnakeLocation((int) (RANDOM.nextDouble() * BOARD_SIZE), (int) (RANDOM.nextDouble() * BOARD_SIZE));
                if (board[locate.getX()][locate.getY()] != 0) {
                    retry++;
                    continue;
                }
                board[locate.getX()][locate.getY()] = 2;
                break;
            }
        }
    }

    private static boolean hasItemOnBoard() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (board[x][y] == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class SnakeLocation {
        private final int x;
        private final int y;

        public SnakeLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public SnakeLocation adjust(int x, int y) {
            return new SnakeLocation(this.x + x, this.y + y);
        }
    }
}
