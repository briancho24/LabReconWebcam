package app.views;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

public class ConsolePanel extends JPanel {

	private JScrollPane consolePane;
	private JTextPane textPane;

	public ConsolePanel() {


		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setFocusable(false);
		textPane.setFont(new Font("Courier New", Font.PLAIN, 10));
		setSize(400, 480);
		setLayout(new GridLayout());

		consolePane = new JScrollPane(textPane);
		consolePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(consolePane);
		redirectSystemStreams();
	}

	private void updateTextPane(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Document doc = textPane.getDocument();
				try {
					doc.insertString(doc.getLength(), text, null);
				} catch (BadLocationException e) {
					throw new RuntimeException(e);
				}
				textPane.setCaretPosition(doc.getLength() - 1);
			}
		});
	}

	private void redirectSystemStreams() {
		//"[" + LocalDateTime.now().toString() + "] " +
		OutputStream out = new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				updateTextPane(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextPane(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}


}
