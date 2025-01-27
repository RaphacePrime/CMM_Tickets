package adminpanels_package;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import classes_package.Evento;
import classes_package.Luogo;
import database_package.EventsDatabase;
import database_package.LuoghiDatabase;
import interfaces_package.NavigationListener;
import utils_package.LookAndFeelUtil;

public class AdminModifyEventPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private List<Evento> eventi;
	private List<Luogo> luoghi;
	private JTable eventTable;
	private DefaultTableModel tableModel;
	private JButton backButton;
	private static Logger logger = LogManager.getLogger(AdminModifyEventPanel.class);
	private NavigationListener navigationListener;
	

	public AdminModifyEventPanel(NavigationListener navigationListener) throws ParseException {
		this.navigationListener = navigationListener;
		LookAndFeelUtil.setCrossPlatformLookAndFeel();
		setLayout(new BorderLayout());
		setBackground(new Color(240, 240, 240));

		JLabel titleLabel = new JLabel("Modifica Eventi", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(new Color(60, 63, 65));
		add(titleLabel, BorderLayout.NORTH);
		fetchAndDisplayEvents();
	}

	public void setBackToAdminHomeAction(ActionListener action) {
		backButton.addActionListener(action);
	}

	public void fetchAndDisplayEvents() throws ParseException {
		eventi = EventsDatabase.getAllEvents();
		luoghi = LuoghiDatabase.getAllLuoghi();
		String[] columnNames = { "Nome Evento", "Data", "Luogo", "Città", "Indirizzo" };
		Object[][] data = new Object[eventi.size()][5];

		for (int i = 0; i < eventi.size(); i++) {
			Evento e = eventi.get(i);
			Luogo l;

			for (int y = 0; y < luoghi.size(); y++) {
				data[i][0] = e.getNome();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				data[i][1] = sdf.format(e.getData());
				l = luoghi.get(y);
				if (e.getIdLuogo() == l.getIdLuogo()) {
					data[i][2] = l.getNome();
					data[i][3] = l.getIndirizzo();
					data[i][4] = l.getCittà();
				}
			}

		}

		tableModel = new DefaultTableModel(data, columnNames);
		eventTable = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if (isCellSelected(row, column)) {
					comp.setBackground(new Color(75, 110, 175));
					comp.setForeground(Color.WHITE);
				} else {
					comp.setBackground(Color.WHITE);
					comp.setForeground(Color.BLACK);
				}
				return comp;
			}
		};

		eventTable.setRowHeight(40);
		eventTable.setFont(new Font("Arial", Font.PLAIN, 14));
		eventTable.setSelectionBackground(new Color(75, 110, 175));
		eventTable.setSelectionForeground(Color.WHITE);

		eventTable.setCellSelectionEnabled(false);
		eventTable.setRowSelectionAllowed(true);
		eventTable.setColumnSelectionAllowed(false);

		eventTable.setDefaultRenderer(Object.class, new ButtonRenderer());

		JTableHeader header = eventTable.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 16));
		header.setBackground(new Color(75, 110, 175));
		header.setForeground(Color.WHITE);

		eventTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent me) {
				int row = eventTable.rowAtPoint(me.getPoint());
				if (row >= 0) {
					String nomeEvento = (String) tableModel.getValueAt(row, 0);
					logger.info("Evento cliccato: " + nomeEvento);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(eventTable);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Eventi"));
		add(scrollPane, BorderLayout.CENTER);
	}

	public void setSwitchToDetailsEventAction() {
		eventTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent me) {
				int row = eventTable.rowAtPoint(me.getPoint());
				if (row >= 0) {
					String nomeLuogo = (String) tableModel.getValueAt(row, 0);
					logger.info("Luogo cliccato: " + nomeLuogo);

					for (Evento evento : eventi) {
						if (evento.getNome().equals(nomeLuogo)) {
							AdminDetailsEventPanel detailsPanel;
							try {
								detailsPanel = new AdminDetailsEventPanel(evento);
								
								navigationListener.AdminNavigateTo(detailsPanel);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		});
	}

	private static class ButtonRenderer extends JButton implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
			setOpaque(true);
			setBorder(BorderFactory.createEmptyBorder());
			setFocusPainted(false);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value.toString());
			if (isSelected) {
				setBackground(new Color(75, 110, 175));
				setForeground(Color.WHITE);
			} else {
				setBackground(Color.WHITE);
				setForeground(Color.BLACK);
			}
			return this;
		}
	}
}
