package com.potato.GUI.Dialog;

import com.potato.GUI.Memory;
import com.potato.Manager.AutoManager;
import com.potato.ToolKit.QuizInformation;
import com.potato.ToolKit.QuizMaker;
import com.potato.Word.Word;
import com.potato.Word.WordHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneOfFourQuizDialog extends JDialog implements QuizDialog
{
    private Random random;
    private AutoManager manager;
    private QuizInformation information;
    private double timeCost;
    private double startTime;

    private QuizMaker maker;
    private int answerIndex;
    private int quizIndex = 0;
    private List<Word> quizList;

    private QuizPassedDialog quizPassedDialog;
    private JPanel wordPanel = new JPanel();
    private JLabel wordLabel = new JLabel();
    private JPanel choicePanel = new JPanel();
    private JPanel jumpPanel = new JPanel();
    private JButton AButton = new JButton();
    private JButton BButton = new JButton();
    private JButton CButton = new JButton();
    private JButton DButton = new JButton();
    private JButton killButton = new JButton("斩");
    private JButton passButton = new JButton("跳过");
    private JButton[] buttons = new JButton[]{AButton, BButton, CButton, DButton};

    /**
     *
     * @param owner
     */
    public OneOfFourQuizDialog(JFrame owner)
    {
        setLayout(new BorderLayout());
        setTitle("四选一测试");
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        random = new Random();
        information = new QuizInformation();
        quizPassedDialog = new QuizPassedDialog(owner, this);

        wordPanel.add(wordLabel);

        choicePanel.setLayout(new GridLayout(2, 2));
        choicePanel.add(AButton);
        choicePanel.add(BButton);
        choicePanel.add(CButton);
        choicePanel.add(DButton);

        jumpPanel.setLayout(new GridLayout(2, 1));
        jumpPanel.add(killButton);
        jumpPanel.add(passButton);

        for (int i = 0; i < buttons.length; i++)
        {
            int finalI = i;
            buttons[i].addActionListener(e -> optionButtonAction(finalI));
        }

        passButton.addActionListener(e ->
        {
            buttonsAction(true, false);
        });

        killButton.addActionListener(e ->
        {
            buttonsAction(true, true);
        });

        add(wordPanel, BorderLayout.NORTH);
        add(choicePanel, BorderLayout.CENTER);
        add(jumpPanel, BorderLayout.EAST);
    }

    public void initial()
    {
        manager = new AutoManager(Memory.chosenWordListFile);
        maker = new QuizMaker(Memory.wordList);
        quizList = maker.randomQuizMaker();
        setQuiz();
    }

    private void optionButtonAction(int index)
    {
        buttonsAction(answerIndex == index, false);
    }

    private void buttonsAction(boolean correct, boolean killed)
    {
        if (quizIndex == 0)
        {
            startTime = System.currentTimeMillis();
        }

        if (!correct)
        {
            manager.modify(quizList.get(quizIndex), WordHelper.updateWord(quizList.get(quizIndex),
                    false, false));
            information.onWrong();

            return;
        }

        manager.modify(quizList.get(quizIndex), WordHelper.updateWord(quizList.get(quizIndex),
                true, killed));
        information.onCorrect();
        quizIndex++;
        if (quizIndex < quizList.size())
        {
            setQuiz();
        }
        else
        {
            timeCost = (System.currentTimeMillis() - startTime) / 1000;
            quizPassedDialog.initial();
            quizPassedDialog.setVisible(true);
            setVisible(false);
            quizIndex = 0;
            manager.push();
            Memory.globalRefreshWithWordListSync();
        }
    }

    private void setQuiz()
    {
        wordLabel.setText("<html><h1>" + quizList.get(quizIndex).getWordName() + "</h1></html>");
        answerIndex = random.nextInt(0, buttons.length);
        buttons[answerIndex].setText(quizList.get(quizIndex).getMeaning());

        for (int i = 0; i < buttons.length; i++)
        {
            if (i != answerIndex)
            {
                Word randomWord = randomWord(quizList.get(quizIndex));
                buttons[i].setText(randomWord.getMeaning());
            }
        }
    }

    private Word randomWord(Word word)
    {
        Word result;
        do
        {
            result = quizList.get(random.nextInt(0, quizList.size()));
        }
        while (result.equals(word));

        return result;
    }

    @Override
    public ArrayList<Integer> getStatistic()
    {
        return information.getStatistic();
    }

    @Override
    public String getDialogName()
    {
        return getTitle();
    }

    @Override
    public double getTimeCost()
    {
        return timeCost;
    }
}
