package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Класс PasswordGenerator представляет графический интерфейс для генерации паролей.
 * Он позволяет пользователю задавать длину пароля, количество заглавных букв и символов,
 * а также генерировать, копировать и сохранять пароли.
 * ~
 * The PasswordGenerator class represents a graphical user interface for generating passwords.
 * It allows the user to set the password length, the number of uppercase letters and symbols,
 * as well as generate, copy, and save passwords.
 */

public class PasswordGenerator extends JFrame {

    private JTextField lengthField;
    private JTextField uppercaseField;
    private JTextField symbolsField;
    private JTextArea passwordArea;

    /**
     * Конструктор класса PasswordGenerator.
     * Инициализирует графический интерфейс и настраивает его компоненты.
     * ~
     * Constructor for the PasswordGenerator class.
     * Initializes the graphical user interface and configures its components.
     */

    public PasswordGenerator() {
        // Инициализации графического интерфейса
        // Initializing the graphical user interface
        setTitle("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 5, 10, 10));
        panel.setBackground(Color.ORANGE); // Изменить цвет фона панели (Change panel background color)
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Добавить отступы

        JLabel lengthLabel = new JLabel("Длина пароля:");
        lengthLabel.setForeground(Color.DARK_GRAY); // Изменить цвет текста метки(Change label text color)
        lengthLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Изменить шрифт и размер(Change font and size)
        panel.add(lengthLabel);
        lengthField = new JTextField("16");
        lengthField.setFont(new Font("Arial", Font.PLAIN, 14)); // Изменить шрифт и размер(Change font and size)
        panel.add(lengthField);

        JLabel upperLabel = new JLabel("Заглавные буквы:");
        upperLabel.setForeground(Color.DARK_GRAY);
        upperLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(upperLabel);
        uppercaseField = new JTextField("1");
        uppercaseField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(uppercaseField);

        JLabel symbolLabel = new JLabel("Символы:");
        symbolLabel.setForeground(Color.DARK_GRAY);
        symbolLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(symbolLabel);
        symbolsField = new JTextField("1");
        symbolsField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(symbolsField);

        JButton generateButton = new JButton("Создать пароль");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });

        panel.add(generateButton);

        JButton copyButton = new JButton("Скопировать пароль");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = passwordArea.getText();
                StringSelection stringSelection = new StringSelection(password);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        panel.add(copyButton);

        JButton saveButton = new JButton("Сохранить пароль");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePasswordToFile();
            }
        });
        panel.add(saveButton);

        passwordArea = new JTextArea() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = Math.max(size.width, getParent().getWidth());
                return size;
            }
        };

        passwordArea.setEditable(false);
        passwordArea.setLineWrap(true);
        passwordArea.setWrapStyleWord(true);
        passwordArea.setForeground(Color.GREEN);
        passwordArea.setBackground(Color.BLACK);
        passwordArea.setFont(new Font("Arial", Font.PLAIN, 35));
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(passwordArea), BorderLayout.CENTER);

    }

    /**
     * Генерирует новый пароль на основе заданных параметров.
     * Длина пароля, количество заглавных букв и символов берутся из соответствующих текстовых полей.
     * Сгенерированный пароль отображается в области passwordArea.
     * ~
     * Generates a new password based on the specified parameters.
     * The password length, number of uppercase letters, and symbols are taken from the corresponding text fields.
     * The generated password is displayed in the passwordArea.
     */

    private void generatePassword() {
        // Генерации пароля
        // Generating the password
        int length = Integer.parseInt(lengthField.getText());
        int uppercase = Integer.parseInt(uppercaseField.getText());
        int symbols = Integer.parseInt(symbolsField.getText());

        StringBuilder password = new StringBuilder();
        Random random = new Random();

        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String symbolChars = "!@#$%^&*()_+-=[]{};':,./<>?";

        // Добавляем заглавные буквы(Adding capital letters)
        for (int i = 0; i < uppercase; i++) {
            password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
        }

        // Добавляем символы(Adding symbols)
        for (int i = 0; i < symbols; i++) {
            password.append(symbolChars.charAt(random.nextInt(symbolChars.length())));
        }

        // Добавляем строчные буквы(Adding lowercase letters)
        int lowercaseCount = length - uppercase - symbols;
        for (int i = 0; i < lowercaseCount; i++) {
            password.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        }

        // Перемешиваем символы в пароле(Shuffling the symbols in the password)
        char[] passwordChars = password.toString().toCharArray();
        for (int i = passwordChars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = passwordChars[i];
            passwordChars[i] = passwordChars[j];
            passwordChars[j] = temp;
        }

        passwordArea.setText(new String(passwordChars));
    }

    /**
     * Сохраняет сгенерированный пароль в файл.
     * Открывает диалоговое окно для выбора файла, в который будет сохранен пароль.
     * Если файл успешно сохранен, выводит сообщение об успешном сохранении.
     * В случае ошибки выводит сообщение об ошибке.
     * ~
     * Saves the generated password to a file.
     * Opens a dialog window for selecting the file to save the password to.
     * If the file is successfully saved, displays a message about successful saving.
     * In case of an error, displays an error message.
     */

    private void savePasswordToFile() {
        // Сохранения пароля в файл
        // Saving the password to a file
        String password = passwordArea.getText();
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(password);
                writer.close();
                JOptionPane.showMessageDialog(this, "Пароль успешно сохранен в файл " + file.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при сохранении пароля в файл: " + ex.getMessage());
            }
        }
    }
}