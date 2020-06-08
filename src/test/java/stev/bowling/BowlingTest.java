package stev.bowling;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Thomas Beauchataud
 * @since 19.05.2020
 * GameDemoTest class containing all tests for the bowling-score.jar library
 */
@SuppressWarnings("ConstantConditions")
class BowlingTest {

    private Game game;
    private NormalFrame normalFrame1;

    /**
     * Initialize a Game and a NormalFrame before each test
     */
    @BeforeEach
    void setUp() {
        this.game = new Game();
        this.normalFrame1 = new NormalFrame(1);
    }

    // ****************************************************
    // ****************************************************
    // *************** TECHNICAL FRAME TEST ***************
    // ****************************************************
    // ****************************************************

    /**
     * Test the creation of a second roll with roll = 1 at frame 1
     * Expecting BowlingException
     */
    @Test
    void testDoingTwiceRoll1() {
        this.normalFrame1.setPinsDown(1, 8);
        assertThrows(BowlingException.class, () -> this.normalFrame1.setPinsDown(1, 1),
                "No exception thrown while trying to create a frame with twice same roll number");
    }

    /**
     * Test the creation of a second roll after a strike at frame 1
     * Expecting BowlingException
     */
    @Test
    void testRollAfterStrikeAtRoll1() {
        this.normalFrame1.setPinsDown(1, 10);
        assertThrows(BowlingException.class, () -> this.normalFrame1.setPinsDown(2, 5),
                "No exception thrown while trying to create a frame with a second roll after a strike");
    }

    /**
     * Test the creation of a third roll on a NormalFrame
     * Expecting BowlingException
     */
    @Test
    void testRollAfterSpareOnNormalFrame() {
        this.normalFrame1.setPinsDown(1, 7).setPinsDown(2, 2);
        assertThrows(BowlingException.class, () -> this.normalFrame1.setPinsDown(3, 5),
                "No exception thrown while trying to create a normal frame with 3 roll");
    }

    /**
     * Test the creation of a third roll on a LastFrame without any spare or strike
     * Expecting BowlingException
     */
    @Test
    void testThirdRollOnLastFrameWithoutStrikeOrSpare() {
        assertThrows(BowlingException.class, () -> this.normalFrame1.setPinsDown(1, 5)
                        .setPinsDown(2, 4).setPinsDown(3, 1),
                "No exception thrown while trying to create a LastFrame with 3 roll without any spare or strike");
    }

    /**
     * Test the creation of a third roll on a LastFrame with a strike
     * Expecting No BowlingException
     */
    @Test
    void testThirdRollOnLastFrameWithStrike() {
        assertDoesNotThrow(() -> this.normalFrame1.setPinsDown(1, 10).setPinsDown(2, 4)
                        .setPinsDown(3, 1),
                "Exception thrown while trying to create a LastFrame with 3rd roll with a strike");
    }

    /**
     * Test the creation of a third roll on a LastFrame with a spare
     * Expecting No BowlingException
     */
    @Test
    void testThirdRollOnLastFrameWithSpare() {
        assertDoesNotThrow(() -> this.normalFrame1.setPinsDown(1, 5).setPinsDown(2, 5)
                        .setPinsDown(3, 1),
                "Exception thrown while trying to create a LastFrame with a 3rd roll with a spare");
    }

    /**
     * Test the creation of a frame with negative frame_number
     * Expecting BowlingException
     */
    @Test
    void testCreationNegativeFrame() {
        assertThrows(BowlingException.class, () -> new NormalFrame(-1),
                "No exception thrown while trying to create a frame with a negative frame number");
    }

    /**
     * Test the creation of a roll with negative roll number
     * Expecting BowlingException
     */
    @Test
    void testCreationNegativeRoll() {
        assertThrows(BowlingException.class, () -> this.normalFrame1.setPinsDown(-1, 5),
                "No exception thrown while trying to create a frame with a negative roll number");
    }

    /**
     * Test the creation of a roll with 0 as roll number
     * Expecting BowlingException
     */
    @Test
    void testCreationZeroRoll() {
        assertThrows(BowlingException.class, () -> this.normalFrame1.setPinsDown(0, 5),
                "No exception thrown while trying to create a frame with a 0 roll number");
    }

    /**
     * Test the creation of a roll with negative score
     * Expecting BowlingException
     */
    @Test
    void testCreationRollNegativeScore() {
        assertThrows(BowlingException.class, () -> this.normalFrame1.setPinsDown(1, -5),
                "No exception thrown while trying to create a frame with a negative score on a roll");
    }

    /**
     * Test the creation of a NormalFrame wih frame_number = 10
     * Expecting BowlingException
     */
    @Test
    void testCreationNormalFrame10() {
        assertThrows(BowlingException.class, () -> new NormalFrame(10),
                "No exception thrown while trying to create a normal frame with 10 as frame number");
    }

    /**
     * Test the creation of a roll with a score of a frame who would be superior to 10
     * Expecting BowlingException
     */
    @Test
    void testScoreSuperiorThan10() {
        this.normalFrame1.setPinsDown(1, 8);
        assertThrows(BowlingException.class, () -> this.normalFrame1.setPinsDown(2, 5),
                "No exception thrown while trying to create a normal frame with a score superior than 10");
    }

    // *****************************************************
    // *****************************************************
    // *************** FUNCTIONAL FRAME TEST ***************
    // *****************************************************
    // *****************************************************

    /**
     * Test the countPinsDown & setPinsDown methods
     * Expecting finding the good number with countPinsDown after calling setPinsDown
     */
    @Test
    void testCountPinsDownSetPinsDown() {
        this.normalFrame1.setPinsDown(1, 8);
        assertEquals(8, this.normalFrame1.countPinsDown(),
                "Method countPinsDown on Frame doesn't not return the good value if there is only 1 roll");

        this.normalFrame1.setPinsDown(2, 1);
        assertEquals(9, this.normalFrame1.countPinsDown(),
                "Method countPinsDown on Frame doesn't not return the good value if there is 2 rolls");
    }

    /**
     * Test the reset method of a Frame
     * Expecting 0 pins down after setting 10 pins down and then resetting the frame
     */
    @Test
    void testResetFrame() {
        this.normalFrame1.setPinsDown(1, 10);
        assertEquals(10, this.normalFrame1.countPinsDown(),
                "Method countPinsDown on Frame doesn't not return the good value if there is strike");

        this.normalFrame1.reset();
        assertEquals(0, this.normalFrame1.countPinsDown(),
                "Method reset on Frame doesn't not reset rolls");
    }

    /**
     * Test the countRoll method of a Frame
     * Expecting finding the same roll number as the number of call of setPinsDown
     */
    @Test
    void testCountRoll() {
        this.normalFrame1.setPinsDown(1, 8);
        assertEquals(1, this.normalFrame1.countRolls(),
                "Method countRolls on Frame doesn't not return the good value if there is only 1 roll");

        this.normalFrame1.setPinsDown(2, 2);
        assertEquals(2, this.normalFrame1.countRolls(),
                "Method countRolls on Frame doesn't not return the good value if there is 2 rolls");
    }

    /**
     * Test the getFrame method method of a Frame
     * Expecting finding the same number as the frame_number is the constructor
     */
    @Test
    void testGetFrameNumber() {
        Frame frame = new NormalFrame(5);
        assertEquals(5, frame.getFrameNumber(),
                "Method getFrameNumber on Frame doesn't not return the good value");
    }

    // ***************************************************
    // ***************************************************
    // *************** TECHNICAL GAME TEST ***************
    // ***************************************************
    // ***************************************************

    /**
     * Test the insertion of a null frame to a game
     * Expecting BowlingException
     */
    @Test
    void testInsertNullFrame() {
        assertThrows(BowlingException.class, () -> this.game.addFrame(null),
                "No exception thrown while trying to create a frame with a negative score on a roll");
    }

    /**
     * Test the insertion of a Frame with not Roll to a game
     * Expecting No BowlingException
     */
    @Test
    void testInsertFrameWithoutRoll() {
        assertDoesNotThrow(() -> this.game.addFrame(new NormalFrame(2)),
                "Exception thrown while trying to add a frame with no pins down");
    }

    /**
     * Test the insertion of two frames with the same frameNumber (frameNumber = 1)
     * Expecting BowlingException
     */
    @Test
    void testInsertTwiceSameFrame() {
        this.game.addFrame(new NormalFrame(1));
        assertThrows(BowlingException.class, () -> this.game.addFrame(new NormalFrame(1)),
                "No exception thrown while trying to add two normal frame with the same frame number");
    }

    /**
     * Test the insertion of a LastFrame just after the creation of the game
     * Expecting BowlingException
     */
    @Test
    void testAddLastFrameFirst() {
        assertThrows(BowlingException.class, () -> this.game.addFrame(new LastFrame(10)),
                "No exception thrown while trying to add a last frame with no normal frame in the game");
    }

    /**
     * Test the insertion of a NormalFrame in last position (10th Frame)
     * Expecting BowlingException
     */
    @Test
    void testAddNormalFrameLast() {
        for (int i = 1; i < 10; ++i) {
            this.game.addFrame(new NormalFrame(i));
        }

        assertThrows(BowlingException.class, () -> this.game.addFrame(new NormalFrame(10)),
                "No exception thrown while trying to add a normal frame with frame_number = 10");
    }

    /**
     * Test the insertion of a 11th LastFrame
     * Expecting BowlingException
     */
    @Test
    void testAddLastFrame11() throws Exception {
        this.initializeGame(9, 8);

        this.game.addFrame(new LastFrame(10));
        assertThrows(BowlingException.class, () -> this.game.addFrame(new LastFrame(11)));
    }

    // ****************************************************
    // ****************************************************
    // *************** FUNCTIONAL GAME TEST ***************
    // ****************************************************
    // ****************************************************

    /**
     * Run a simple game without spare and strike and checking the cumulative score method validity
     * Expecting getCumulativeScore valid (equals to the sum of roll score of previous frame) during the all game
     */
    @Test
    void testSimpleGame() {
        final int rollScore = 4;

        // Setting frame from 1 -> 9
        for (int i = 1; i < 10; ++i) {
            this.game.addFrame(new NormalFrame(i).setPinsDown(1, rollScore).setPinsDown(2, rollScore));
            assertEquals(i * 2 * rollScore, this.game.getCumulativeScore(i));
        }

        // Setting 10th frame
        this.game.addFrame(new LastFrame(10).setPinsDown(1, rollScore).setPinsDown(2, rollScore));
        assertEquals(10 * 2 * rollScore, this.game.getCumulativeScore(10));
    }

    /**
     * Run a game with some normal frame and one spare and test the cumulative score without spare bonus
     * Expecting getCumulativeScore valid for each frame
     *
     * @throws Exception if initializeGame method fail
     */
    @Test
    void testScoreJustAfterSpare() throws Exception {
        final int frameScore = 8;
        final int frameSpareNumber = 7;

        // Setting frame from 1 -> 6
        int expectedScore6 = this.initializeGame(frameSpareNumber - 1, frameScore);
        assertEquals(expectedScore6, this.game.getCumulativeScore(frameSpareNumber - 1));

        // Spare on 7th frame (8 + 2)
        this.game.addFrame(new NormalFrame(frameSpareNumber).setPinsDown(1, 8).setPinsDown(2, 2));
        int expectedScore7 = ((frameSpareNumber - 1) * frameScore) + 10;
        assertEquals(expectedScore7, this.game.getCumulativeScore(frameSpareNumber));
    }

    /**
     * Run a game with some normal frame and one strike and test the cumulative score without strike bonus
     * Expecting getCumulativeScore valid for each frame
     *
     * @throws Exception if initializeGame method fail
     */
    @Test
    void testScoreJustAfterStrike() throws Exception {
        final int frameScore = 8;
        final int frameSpareNumber = 7;

        // Setting frame from 1 -> 6
        int expectedScore6 = this.initializeGame(frameSpareNumber - 1, frameScore);
        assertEquals(expectedScore6, this.game.getCumulativeScore(frameSpareNumber - 1));

        // Spare on 7th frame (8 + 2)
        this.game.addFrame(new NormalFrame(frameSpareNumber).setPinsDown(1, 10));
        int expectedScore7 = ((frameSpareNumber - 1) * frameScore) + 10;
        assertEquals(expectedScore7, this.game.getCumulativeScore(frameSpareNumber));
    }

    /**
     * Run a game with one spare and without strike and checking the cumulative score method validity
     * Expecting getCumulativeScore valid for each frame
     *
     * @throws Exception if initializeGame or finalizeGame method fail
     */
    @Test
    void testSpareInGameOnNormalFrame() throws Exception {
        final int frameScore = 8;
        final int frameSpareNumber = 7;
        final int scoreAfterStrike = 8;

        // Setting frame from 1 -> 6
        int expectedScore6 = this.initializeGame(frameSpareNumber - 1, frameScore);
        assertEquals(expectedScore6, this.game.getCumulativeScore(frameSpareNumber - 1));

        // Spare on 7th frame (8 + 2)
        this.game.addFrame(new NormalFrame(frameSpareNumber).setPinsDown(1, 8).setPinsDown(2, 2));
        int expectedScore7 = expectedScore6 + 10;

        // Setting 8th frame & checking the new score of the 7th frame
        this.game.addFrame(new NormalFrame(frameSpareNumber + 1)
                .setPinsDown(1, (scoreAfterStrike / 2)).setPinsDown(2, (scoreAfterStrike / 2)));
        expectedScore7 += (scoreAfterStrike / 2);
        assertEquals(expectedScore7, this.game.getCumulativeScore(frameSpareNumber));
        final int expectedScore8 = expectedScore7 + scoreAfterStrike;
        assertEquals(expectedScore8, this.game.getCumulativeScore(frameSpareNumber + 1));

        // Setting frame from 9 -> 10
        int scoreGenerated = this.finalizeGame(frameScore);
        final int expectedScore10 = expectedScore8 + scoreGenerated;
        assertEquals(expectedScore10, this.game.getCumulativeScore(10));
    }

    /**
     * Run a game with one strike and without spare and checking the cumulative score method validity
     * Expecting getCumulativeScore valid for each frame
     *
     * @throws Exception if initializeGame or finalizeGame method fail
     */
    @Test
    void testStrikeInGameOnNormalFrame() throws Exception {
        final int frameScore = 8;
        final int frameStrikeNumber = 6;
        final int score1AfterStrike = 3;
        final int score2AfterStrike = 5;

        // Setting frame from 1 -> 5
        int expectedScore5 = this.initializeGame(frameStrikeNumber - 1, frameScore);
        assertEquals(expectedScore5, this.game.getCumulativeScore(frameStrikeNumber - 1));

        // Strike on 6th frame
        this.game.addFrame(new NormalFrame(frameStrikeNumber).setPinsDown(1, 10));
        int expectedScore6 = expectedScore5 + 10;

        // Setting 7th frame & checking the new score of the 6th frame and of the 7th frame
        this.game.addFrame(new NormalFrame(frameStrikeNumber + 1)
                .setPinsDown(1, score1AfterStrike).setPinsDown(2, score2AfterStrike));
        expectedScore6 += score1AfterStrike + score2AfterStrike;
        assertEquals(expectedScore6, this.game.getCumulativeScore(frameStrikeNumber),
                "Strike frame cumulative score doesn't get bonus of the two rolls after it");
        int expectedScore7 = expectedScore6 + score1AfterStrike + score2AfterStrike;
        assertEquals(expectedScore7, this.game.getCumulativeScore(frameStrikeNumber + 1),
                "Frame after strike does not update cumulative score after giving bonus to the strike frame score");

        // Setting frame from 8 -> 10
        int scoreGenerated = this.finalizeGame(frameScore);
        final int expectedScore10 = expectedScore7 + scoreGenerated;
        assertEquals(expectedScore10, this.game.getCumulativeScore(10));
    }

    /**
     * Run a game with two following strike and without spare and checking the cumulative score method validity
     * Expecting getCumulativeScore valid for each frame
     *
     * @throws Exception if initializeGame or finalizeGame method fail
     */
    @Test
    void testTwoFollowingStrike() throws Exception {
        final int frameScore = 6;
        final int frameStrike1Number = 6;
        final int frameStrike2Number = 7;
        final int score1AfterStrike2 = 4;
        final int score2AfterStrike2 = 3;

        // Setting frame from 1 -> 5
        int expectedScore5 = this.initializeGame(frameStrike1Number - 1, frameScore);
        assertEquals(expectedScore5, this.game.getCumulativeScore(frameStrike1Number - 1));

        // Strike on 6th frame
        this.game.addFrame(new NormalFrame(frameStrike1Number).setPinsDown(1, 10));
        int expectedScore6 = expectedScore5 + 10;

        // Strike on 7th frame
        this.game.addFrame(new NormalFrame(frameStrike2Number).setPinsDown(1, 10));
        expectedScore6 += 10;
        assertEquals(expectedScore6, this.game.getCumulativeScore(frameStrike1Number));
        int expectedScore7 = expectedScore6 + 10;

        // Setting 8th frame & checking the new score of the 6th frame, of the 7th frame and of the 8th frame
        this.game.addFrame(new NormalFrame(frameStrike2Number + 1)
                .setPinsDown(1, score1AfterStrike2).setPinsDown(2, score2AfterStrike2));
        expectedScore6 += score1AfterStrike2;
        expectedScore7 += score1AfterStrike2 + score2AfterStrike2;
        int expectedScore8 = expectedScore7 + score1AfterStrike2 + score2AfterStrike2;
        assertEquals(expectedScore6, this.game.getCumulativeScore(frameStrike1Number),
                "During a game with two following strikes, the first one doesn't get his second bonus");
        assertEquals(expectedScore7, this.game.getCumulativeScore(frameStrike2Number));
        assertEquals(expectedScore8, this.game.getCumulativeScore(frameStrike2Number + 1));

        // Setting frame 9 -> 10
        int scoreGenerated = this.finalizeGame(frameScore);
        final int expectedScore10 = expectedScore8 + scoreGenerated;
        assertEquals(expectedScore10, this.game.getCumulativeScore(10));
    }

    /**
     * Run a game with a spare followed with strike and checking the cumulative score method validity
     * Expecting getCumulativeScore valid for each frame
     *
     * @throws Exception if initializeGame or finalizeGame method fail
     */
    @Test
    void testSpareThenStrike() throws Exception {
        final int frameScore = 6;
        final int frameSpareNumber = 6;
        final int frameStrikeNumber = 7;

        // Setting frame from 1 -> 5
        int expectedScore5 = this.initializeGame(frameSpareNumber - 1, frameScore);
        assertEquals(expectedScore5, this.game.getCumulativeScore(frameSpareNumber - 1));

        // Spare on 6th frame (8 + 2)
        this.game.addFrame(new NormalFrame(frameSpareNumber).setPinsDown(1, 8).setPinsDown(2, 2));
        int expectedScore6 = expectedScore5 + 10;

        // Strike on 7th frame
        this.game.addFrame(new NormalFrame(frameStrikeNumber).setPinsDown(1, 10));
        expectedScore6 += 10;
        assertEquals(expectedScore6, this.game.getCumulativeScore(frameSpareNumber));
    }

    /**
     * Run a normal game with 3 strike on the last frame and checking the cumulative score method validity
     * Expecting getCumulativeScore valid for last frame
     *
     * @throws Exception if initializeGame or finalizeGame method fail
     */
    @Test
    void testFullStrikeOnLast() throws Exception {
        final int frameScore = 6;
        final int frameStrikeNumber = 10;

        // Setting frame from 1 -> 9
        int expectedScore9 = this.initializeGame(frameStrikeNumber - 1, frameScore);
        assertEquals(expectedScore9, this.game.getCumulativeScore(frameStrikeNumber - 1));

        // Strike on 10th frame 1st roll
        LastFrame lf = new LastFrame(frameStrikeNumber).setPinsDown(1, 10);
        this.game.addFrame(lf);
        int expectedScore10 = expectedScore9 + 10;

        // Strike on 10th frame 2nd roll
        lf.setPinsDown(2, 10);
        expectedScore10 = expectedScore10 + 10 + 10;

        // Strike on 10th frame 3rd roll
        lf.setPinsDown(3, 10);
        expectedScore10 = expectedScore10 + 10 + 10;

        assertEquals(expectedScore10, this.game.getCumulativeScore(frameStrikeNumber));
    }

    // ****************************************************
    // ****************************************************
    // *********************** UTILS **********************
    // ****************************************************
    // ****************************************************

    /**
     * Initialize a game with normal frames
     *
     * @param frameCount int The number of Frame to create (between 1 & 9)
     * @param frameScore int The score of each created Frame (in (2, 4, 6, 8))
     * @return int The score generated
     * @throws Exception If conditions are not respected or if games already has frames
     */
    private int initializeGame(int frameCount, int frameScore) throws Exception {
        if ((frameScore % 2) != 0 && frameScore > 8 || frameScore < 2) {
            throw new Exception("Argument 2 frameScore must be in (2, 4, 6, 8)");
        }
        if (frameCount < 0 || frameCount > 9) {
            throw new Exception("Argument 1 frameCount must be between 1 and 9");
        }
        if (this.game.m_frames.size() != 0) {
            throw new Exception("Impossible to initialize a game with frames in it");
        }
        final int rollScore = frameScore / 2;
        int scoreCreated = 0;
        for (int i = 1; i <= frameCount; i++) {
            this.game.addFrame(new NormalFrame(i).setPinsDown(1, rollScore).setPinsDown(2, rollScore));
            scoreCreated += rollScore * 2;
        }
        return scoreCreated;
    }

    /**
     * Finalize the game with normal frames & a last frame
     *
     * @param frameScore int The score of each created Frame (in (2, 4, 6, 8))
     * @return int The score generated
     * @throws Exception If conditions are not respected of if game already has 10 frames
     */
    private int finalizeGame(int frameScore) throws Exception {
        if ((frameScore % 2) != 0 && frameScore > 8 || frameScore < 2) {
            throw new Exception("Argument 2 frameScore must be in (2, 4, 6, 8)");
        }
        if (this.game.m_frames.size() > 10) {
            throw new Exception("Impossible to finalize a game with 10 frames or more");
        }
        int scoreCreated = 0;
        final int rollScore = frameScore / 2;
        for (int i = this.game.m_frames.size() + 1; i < 10; i++) {
            this.game.addFrame(new NormalFrame(i).setPinsDown(1, rollScore).setPinsDown(2, rollScore));
            scoreCreated += rollScore * 2;
        }
        this.game.addFrame(new LastFrame(10).setPinsDown(1, rollScore).setPinsDown(2, rollScore));
        scoreCreated += rollScore * 2;
        return scoreCreated;
    }
}
