import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JTextPane;

// TODO: Auto-generated Javadoc
/**
 * Klasa odpowiedzialna za klienta gry.
 */
public class QuizGame extends JFrame implements GameProtokol {


	private static final long serialVersionUID = 1L;


	private JPanel contentPane;
	private JTextField serwer;
	private JTextField port;
	private JTextField nick;
	private JTextArea komunikaty;
	private JLabel tresc;
	private JButton polacz;
	private JButton rozlacz;
	private JButton rozpocznijRunde;
	private JButton pisz;
	private JButton dolacz;
	private JButton wyslij;
	private JRadioButton odpA;
	private JRadioButton odpB;
	private JRadioButton odpC;
	private JRadioButton odpD;

	/** Czy polaczony. */
	private boolean polaczony = false;
	
	/** Utworzenie watku Klient. */
	private Klient watekKlienta;
	
	/** lista zalogowanych. */
	private DefaultListModel<String> listaZalogowanych;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField czat;
	private JLabel lblCzat;
	private JList<String> listaGraczy_1;

	/**
	 * Uruchomienie aplikacji.
	 *
	 * @param args 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizGame frame = new QuizGame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * GUI i akcje przyciskow.
	 */
	public QuizGame() {
		super("Klient");
		setTitle("QuizGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 907, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblSerwer = new JLabel("Serwer:");
		lblSerwer.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblSerwer = new GridBagConstraints();
		gbc_lblSerwer.insets = new Insets(0, 0, 5, 5);
		gbc_lblSerwer.anchor = GridBagConstraints.EAST;
		gbc_lblSerwer.gridx = 0;
		gbc_lblSerwer.gridy = 0;
		panel.add(lblSerwer, gbc_lblSerwer);

		serwer = new JTextField();
		serwer.setText("localhost");
		GridBagConstraints gbc_serwer = new GridBagConstraints();
		gbc_serwer.fill = GridBagConstraints.HORIZONTAL;
		gbc_serwer.insets = new Insets(0, 0, 5, 5);
		gbc_serwer.gridx = 1;
		gbc_serwer.gridy = 0;
		panel.add(serwer, gbc_serwer);
		serwer.setColumns(10);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 2;
		gbc_lblPort.gridy = 0;
		panel.add(lblPort, gbc_lblPort);

		port = new JTextField();
		port.setText("2345");
		GridBagConstraints gbc_port = new GridBagConstraints();
		gbc_port.insets = new Insets(0, 0, 5, 5);
		gbc_port.fill = GridBagConstraints.HORIZONTAL;
		gbc_port.gridx = 3;
		gbc_port.gridy = 0;
		panel.add(port, gbc_port);
		port.setColumns(10);

		polacz = new JButton("PO\u0141ACZ");
		polacz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				polaczony = true;
				watekKlienta = new Klient();
				watekKlienta.start();
				polacz.setEnabled(false);
				rozlacz.setEnabled(true);
				serwer.setEnabled(false);
				port.setEnabled(false);
				dolacz.setEnabled(true);
				nick.setEnabled(true);

			}
		});
		GridBagConstraints gbc_polacz = new GridBagConstraints();
		gbc_polacz.fill = GridBagConstraints.HORIZONTAL;
		gbc_polacz.insets = new Insets(0, 0, 5, 5);
		gbc_polacz.gridx = 4;
		gbc_polacz.gridy = 0;
		panel.add(polacz, gbc_polacz);

		rozlacz = new JButton("ROZ\u0141ACZ");
		rozlacz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				polacz.setEnabled(true);
				rozlacz.setEnabled(false);
				serwer.setEnabled(true);
				port.setEnabled(true);
				dolacz.setEnabled(false);
				nick.setEnabled(false);
				polaczony = false;
			}
		});
		rozlacz.setEnabled(false);
		GridBagConstraints gbc_rozlacz = new GridBagConstraints();
		gbc_rozlacz.anchor = GridBagConstraints.WEST;
		gbc_rozlacz.insets = new Insets(0, 0, 5, 5);
		gbc_rozlacz.gridx = 5;
		gbc_rozlacz.gridy = 0;
		panel.add(rozlacz, gbc_rozlacz);

		JLabel lblPodajSwjNick = new JLabel("Podaj sw\u00F3j nick: ");
		lblPodajSwjNick.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblPodajSwjNick = new GridBagConstraints();
		gbc_lblPodajSwjNick.anchor = GridBagConstraints.EAST;
		gbc_lblPodajSwjNick.insets = new Insets(0, 0, 0, 5);
		gbc_lblPodajSwjNick.gridx = 0;
		gbc_lblPodajSwjNick.gridy = 1;
		panel.add(lblPodajSwjNick, gbc_lblPodajSwjNick);

		nick = new JTextField();
		nick.setText("nick");
		nick.setEnabled(false);
		GridBagConstraints gbc_nick = new GridBagConstraints();
		gbc_nick.insets = new Insets(0, 0, 0, 5);
		gbc_nick.fill = GridBagConstraints.HORIZONTAL;
		gbc_nick.gridx = 1;
		gbc_nick.gridy = 1;
		panel.add(nick, gbc_nick);
		nick.setColumns(10);

		dolacz = new JButton("Do\u0142\u0105cz do gry");
		dolacz.setEnabled(false);
		dolacz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				nick.setEnabled(false);
				dolacz.setEnabled(false);
				pisz.setEnabled(true);
				rozpocznijRunde.setEnabled(true);
				watekKlienta.wyslij(nick.getText(), NICK_COMMAND);
			}
		});
		// dolacz.setBackground(new Color(50, 205, 50));
		GridBagConstraints gbc_dolacz = new GridBagConstraints();
		gbc_dolacz.insets = new Insets(0, 0, 0, 5);
		gbc_dolacz.gridx = 2;
		gbc_dolacz.gridy = 1;
		panel.add(dolacz, gbc_dolacz);

		rozpocznijRunde = new JButton("ROZPOCZNIJ RUNDE");
		rozpocznijRunde.setEnabled(false);
		rozpocznijRunde.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				watekKlienta.wyslij("", READY_COMMAND);
				rozpocznijRunde.setEnabled(false);
			}
		});

		JLabel lblGdyBdzieszGotw = new JLabel(
				"Gdy b\u0119dziesz got\u00F3w kliknij: ");
		lblGdyBdzieszGotw.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblGdyBdzieszGotw = new GridBagConstraints();
		gbc_lblGdyBdzieszGotw.insets = new Insets(0, 0, 0, 5);
		gbc_lblGdyBdzieszGotw.gridx = 5;
		gbc_lblGdyBdzieszGotw.gridy = 1;
		panel.add(lblGdyBdzieszGotw, gbc_lblGdyBdzieszGotw);
		GridBagConstraints gbc_rozpocznijRunde = new GridBagConstraints();
		gbc_rozpocznijRunde.gridx = 6;
		gbc_rozpocznijRunde.gridy = 1;
		panel.add(rozpocznijRunde, gbc_rozpocznijRunde);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setLayout(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);

		/*
		 * JLabel lblPytanieNr = new JLabel("Pytanie nr: ");
		 * lblPytanieNr.setBounds(0, 0, 66, 14); panel_1.add(lblPytanieNr);
		 * 
		 * JLabel lblNrpyt = new JLabel("x"); lblNrpyt.setBounds(57, 0, 46, 14);
		 * panel_1.add(lblNrpyt);
		 */

		tresc = new JLabel("Oczekiwanie na przydzia³ pytania...");
		tresc.setFont(new Font("Arial Black", Font.PLAIN, 13));
		tresc.setBounds(0, 25, 550, 14);
		panel_1.add(tresc);

		wyslij = new JButton("Wy\u015Blij odpowied\u017A!");
		wyslij.setEnabled(false);
		wyslij.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				boolean wybrano = false;
				if (odpA.isSelected()) {
					watekKlienta.wyslij("a", ANSWER_COMMAND);
					wybrano = true;
				}
				if (odpB.isSelected()) {
					watekKlienta.wyslij("b", ANSWER_COMMAND);
					wybrano = true;
				}
				if (odpC.isSelected()) {
					watekKlienta.wyslij("c", ANSWER_COMMAND);
					wybrano = true;
				}
				if (odpD.isSelected()) {
					watekKlienta.wyslij("d", ANSWER_COMMAND);
					wybrano = true;
				}
				if (wybrano) {
					wyslij.setEnabled(false);
					rozpocznijRunde.setEnabled(true);
				}
			}
		});
		wyslij.setBounds(151, 132, 192, 23);
		panel_1.add(wyslij);

		odpA = new JRadioButton("A");
		odpA.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(odpA);
		odpA.setBounds(32, 59, 208, 23);
		panel_1.add(odpA);

		odpB = new JRadioButton("B");
		odpB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(odpB);
		odpB.setBounds(268, 59, 222, 23);
		panel_1.add(odpB);

		odpC = new JRadioButton("C");
		odpC.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(odpC);
		odpC.setBounds(32, 102, 208, 23);
		panel_1.add(odpC);

		odpD = new JRadioButton("D");
		odpD.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(odpD);
		odpD.setBounds(268, 102, 222, 23);
		panel_1.add(odpD);

		JList<String> listaGraczy = new JList<String>();
		listaZalogowanych = new DefaultListModel<String>();
		listaGraczy_1 = new JList<String>(listaZalogowanych);
		listaGraczy_1.setBackground(new Color(255, 255, 255));
		listaGraczy_1.setFixedCellWidth(120);
		listaGraczy_1.setBounds(675, 213, 177, 184);
		panel_1.add(listaGraczy_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 213, 645, 184);
		panel_1.add(scrollPane);

		komunikaty = new JTextArea();
		komunikaty.setBackground(SystemColor.inactiveCaptionBorder);
		komunikaty.setText("test");
		scrollPane.setViewportView(komunikaty);

		czat = new JTextField();
		czat.setBackground(SystemColor.textHighlightText);
		czat.setBounds(20, 397, 645, 20);
		panel_1.add(czat);
		czat.setColumns(10);

		pisz = new JButton("Pisz (enter)");
		pisz.setEnabled(false);
		pisz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				watekKlienta.wyslij(czat.getText(), POST_COMMAND);
				czat.setText("");
			}
		});
		pisz.setBounds(675, 396, 177, 23);
		panel_1.add(pisz);
		
		lblCzat = new JLabel("CZAT");
		lblCzat.setFont(new Font("Aharoni", Font.PLAIN, 21));
		lblCzat.setBackground(Color.GRAY);
		lblCzat.setBounds(20, 178, 200, 50);
		panel_1.add(lblCzat);
		
		JLabel lblGracze = new JLabel("GRACZE");
		lblGracze.setFont(new Font("Aharoni", Font.PLAIN, 21));
		lblGracze.setBackground(Color.GRAY);
		lblGracze.setBounds(671, 178, 200, 50);
		panel_1.add(lblGracze);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(691, 29, 161, 138);
		panel_1.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblQuizgameV = new JLabel("QuizGame v.1");
		lblQuizgameV.setFont(new Font("Aharoni", Font.PLAIN, 15));
		panel_2.add(lblQuizgameV);
		
		JLabel lblAutorDataPawe = new JLabel("Autor: Data Pawe\u0142");
		panel_2.add(lblAutorDataPawe);
		
		JLabel lblKrdzisio = new JLabel("KrDzIs3011Io");
		panel_2.add(lblKrdzisio);
		
		JTextPane textPane = new JTextPane();
		panel_2.add(textPane);
		
		JLabel lblProjektZaliczeniowyNa = new JLabel((String) null);
		panel_2.add(lblProjektZaliczeniowyNa);
		
		JLabel lblA = new JLabel("A");
		lblA.setFont(new Font("Aharoni", Font.PLAIN, 16));
		lblA.setBounds(20, 63, 46, 14);
		panel_1.add(lblA);
		
		JLabel lblC = new JLabel("C");
		lblC.setFont(new Font("Aharoni", Font.PLAIN, 16));
		lblC.setBounds(20, 106, 46, 14);
		panel_1.add(lblC);
		
		JLabel lblB = new JLabel("B");
		lblB.setFont(new Font("Aharoni", Font.PLAIN, 16));
		lblB.setBounds(257, 63, 46, 14);
		panel_1.add(lblB);
		
		JLabel lblD = new JLabel("D");
		lblD.setFont(new Font("Aharoni", Font.PLAIN, 16));
		lblD.setBounds(257, 106, 46, 14);
		panel_1.add(lblD);

	}

	/**
	 * Klasa obslugujaca polaczenie z serwerem gry.
	 */
	private class Klient extends Thread implements GameProtokol {
		
		/** Socket. */
		private Socket socket;
		
		/** Strumien wejscie. */
		private BufferedReader wejscie;
		
		/** Strumien wyjscie. */
		private PrintWriter wyjscie;

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			try {
				socket = new Socket(serwer.getText(), new Integer(
						port.getText()));
				wyswietlKomunikat("Po³¹czono z serwerem.");

				wejscie = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				wyjscie = new PrintWriter(new OutputStreamWriter(
						socket.getOutputStream()), true);

				wyslij("", LOGIN_COMMAND);

				String lancuch = null;

				while (polaczony) {
					lancuch = wejscie.readLine();

					if (lancuch.startsWith(POST_COMMAND)) {
						wyswietlKomunikat(lancuch.substring(POST_COMMAND
								.length()));
					}

					if (lancuch.startsWith(QUESTION_COMMAND)) {
						tresc.setText(lancuch.substring(QUESTION_COMMAND
								.length()));
						wyslij.setEnabled(true);

					}
					if (lancuch.startsWith(VAR_A_COMMAND)) {
						odpA.setText(lancuch.substring(VAR_A_COMMAND.length()));

					}
					if (lancuch.startsWith(VAR_B_COMMAND)) {
						odpB.setText(lancuch.substring(VAR_B_COMMAND.length()));

					}
					if (lancuch.startsWith(VAR_C_COMMAND)) {
						odpC.setText(lancuch.substring(VAR_C_COMMAND.length()));

					}
					if (lancuch.startsWith(VAR_D_COMMAND)) {
						odpD.setText(lancuch.substring(VAR_D_COMMAND.length()));

					}
					if (lancuch.startsWith(TRUE_COMMAND)) {
						odpD.setText(lancuch.substring(VAR_D_COMMAND.length()));

					}

					if (lancuch.startsWith(NICKLIST_COMMAND)) {

						StringTokenizer uzytkownicy = new StringTokenizer(
								lancuch.substring(NICKLIST_COMMAND.length()),
								",");

						listaZalogowanych.clear();

						System.out.println(uzytkownicy.countTokens());
						System.out.println(listaZalogowanych);

						int iloscOsob = uzytkownicy.countTokens();
						for (int i = 0; i < iloscOsob; i++) {
							listaZalogowanych.add(i, uzytkownicy.nextToken());
						}

						System.out.println(listaZalogowanych);
					}
					if (lancuch.startsWith(LOGOUT_COMMAND)) {
						wyswietlKomunikat(lancuch.substring(LOGOUT_COMMAND
								.length()));

						listaZalogowanych.clear();
						polaczony = false;
						rozlacz.setEnabled(false);
						polacz.setEnabled(true);
						serwer.setEnabled(true);
						port.setEnabled(true);
					}
				}
			} catch (UnknownHostException e) {
				wyswietlKomunikat("B³¹d po³¹czenia!");
			} catch (IOException e) {
				wyswietlKomunikat(e.toString());
			} finally {
				try {
					wejscie.close();
					wyjscie.close();
					socket.close();
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Wyslij do serwera.
		 *
		 * @param tekst tekst
		 * @param protokol protokol
		 */
		public void wyslij(String tekst, String protokol) {
			wyjscie.println(protokol + tekst);
		}
	}

	/**
	 * Wyswietl komunikat u klienta.
	 *
	 * @param tekst tekst
	 */
	private void wyswietlKomunikat(String tekst) {
		komunikaty.append(tekst + "\n");
		komunikaty.setCaretPosition(komunikaty.getDocument().getLength());
	}
}
