/*
 * HistoryFrame.java
 *
 * Created on 27. prosinec 2007, 12:22
 */
package esmska.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.jvnet.substance.SubstanceLookAndFeel;

import esmska.ThemeManager;
import esmska.data.Config;
import esmska.data.History;
import esmska.persistence.PersistenceManager;
import esmska.utils.AbstractDocumentListener;
import esmska.utils.ActionEventSupport;
import esmska.utils.OSType;
import javax.swing.KeyStroke;

/** Display all sent messages in a frame
 *
 * @author  ripper
 */
public class HistoryFrame extends javax.swing.JFrame {

    public static final int ACTION_RESEND_SMS = 0;
    private static final String RES = "/esmska/resources/";
    private static final Logger logger = Logger.getLogger(HistoryFrame.class.getName());
    private static final Config config = PersistenceManager.getConfig();
    private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
    private History history = PersistenceManager.getHistory();
    private HistoryTableModel historyTableModel = new HistoryTableModel();
    private TableRowSorter<HistoryTableModel> historyTableSorter = new TableRowSorter<HistoryTableModel>(historyTableModel);
    private HistoryRowFilter historyTableFilter = new HistoryRowFilter();
    private Action deleteAction = new DeleteAction();
    private Action resendAction = new ResendAction();
    private History.Record selectedHistory;

    // <editor-fold defaultstate="collapsed" desc="ActionEvent support">
    private ActionEventSupport actionSupport = new ActionEventSupport(this);
    public void addActionListener(ActionListener actionListener) {
        actionSupport.addActionListener(actionListener);
    }
    
    public void removeActionListener(ActionListener actionListener) {
        actionSupport.removeActionListener(actionListener);
    }
    // </editor-fold>
    
    /** Creates new form HistoryFrame */
    public HistoryFrame() {
        initComponents();
        //if not Substance LaF, add clipboard popup menu to text components
        if (!PersistenceManager.getConfig().getLookAndFeel().equals(ThemeManager.LAF_SUBSTANCE)) {
            ClipboardPopupMenu.register(searchField);
            ClipboardPopupMenu.register(textArea);
        }
        
        //select first row
        if (historyTableModel.getRowCount() > 0) {
            historyTable.getSelectionModel().setSelectionInterval(0, 0);
        }
        history.addActionListener(new HistoryActionListener());
        historyTable.requestFocusInWindow();
    }

    /** Return currently selected sms history */
    public History.Record getSelectedHistory() {
        return selectedHistory;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        numberLabel = new javax.swing.JLabel();
        operatorLabel = new javax.swing.JLabel();
        senderNumberLabel = new javax.swing.JLabel();
        senderNameLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        deleteButton = new javax.swing.JButton();
        resendButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Historie odeslaných zpráv - Esmska");
        setIconImage(new ImageIcon(getClass().getResource(RES + "history-48.png")).getImage());

        historyTable.setModel(historyTableModel);
        historyTable.setDefaultRenderer(Date.class, new TableDateRenderer());
        historyTable.getSelectionModel().addListSelectionListener(new HistoryTableListener());

        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        historyTableSorter.setSortKeys(sortKeys);
        historyTable.setRowSorter(historyTableSorter);
        historyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historyTableMouseClicked(evt);
            }
        });
        historyTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                historyTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(historyTable);

        jLabel2.setText("Jméno:");

        jLabel1.setText("Číslo:");

        jLabel3.setText("Datum:");

        jLabel4.setText("Operátor:");

        jLabel5.setText("Číslo odes.:");

        jLabel6.setText("Jméno odes.:");

        dateLabel.setText("    ");

        nameLabel.setForeground(new java.awt.Color(0, 51, 255));
        nameLabel.setText("    ");

        numberLabel.setText("    ");

        operatorLabel.setText("    ");

        senderNumberLabel.setText("    ");

        senderNameLabel.setText("    ");

        textArea.setLineWrap(true);
        jScrollPane2.setViewportView(textArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(operatorLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numberLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(senderNumberLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(senderNameLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dateLabel, nameLabel, numberLabel, operatorLabel, senderNameLabel, senderNumberLabel});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(numberLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(operatorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(senderNumberLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(senderNameLabel)))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        deleteButton.setAction(deleteAction);
        deleteButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        deleteButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);

        resendButton.setAction(resendAction);
        resendButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        resendButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/esmska/resources/close-22.png"))); // NOI18N
        closeButton.setMnemonic('z');
        closeButton.setText("Zavřít");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        searchField.setColumns(15);
        searchField.setToolTipText("Zadejte výraz pro vyhledání v historii zpráv");
        searchField.getDocument().addDocumentListener(new AbstractDocumentListener() {
            @Override
            public void onUpdate(DocumentEvent e) {
                historyTableFilter.requestUpdate();
            }
        });

        //on Mac OS X this will create a native search field with inline icons
        searchField.putClientProperty("JTextField.variant", "search");

        //clear text on escape
        String command = "clear";
        searchField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), command);
        searchField.getActionMap().put(command, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText(null);
            }
        });
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFieldFocusGained(evt);
            }
        });

        jLabel7.setDisplayedMnemonic('h');
        jLabel7.setLabelFor(searchField);
        jLabel7.setText("Hledat:");
        jLabel7.setToolTipText(searchField.getToolTipText());

        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/esmska/resources/clear-22.png"))); // NOI18N
        clearButton.setMnemonic('v');
        clearButton.setToolTipText("Vyčistit hledaný výraz (Alt+V, Escape)");
        clearButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        clearButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);

        // on Mac OS X the search field has native look and feel with inline icons, clear
        // button is not needed
        if (OSType.isEqual(OSType.MAC_OS_X) && config.getLookAndFeel().equals(ThemeManager.LAF_SYSTEM)) {
            clearButton.setVisible(false);
        }
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteButton)
                            .addComponent(resendButton)
                            .addComponent(closeButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, resendButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(clearButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(deleteButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(resendButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {deleteButton, resendButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.setVisible(false);
        this.dispose();
}//GEN-LAST:event_closeButtonActionPerformed

    private void historyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyTableMouseClicked
        if (evt.getClickCount() != 2) { //only on double click
            return;
        }
        resendButton.doClick(0);
    }//GEN-LAST:event_historyTableMouseClicked

    private void historyTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_historyTableKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            evt.consume();
            resendButton.doClick(0);
        }
    }//GEN-LAST:event_historyTableKeyPressed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        searchField.setText(null);
        searchField.requestFocusInWindow();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void searchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFieldFocusGained
        searchField.selectAll();
    }//GEN-LAST:event_searchFieldFocusGained

    /** Delete sms from history */
    private class DeleteAction extends AbstractAction {
        private final String deleteOption = "Odstranit";
        private final String cancelOption = "Zrušit";
        private final String[] options = new String[]{cancelOption, deleteOption};
        
        public DeleteAction() {
            super(null, new ImageIcon(HistoryFrame.class.getResource(RES + "delete-22.png")));
            this.putValue(SHORT_DESCRIPTION, "Odstranit označené zprávy z historie (Alt+D)");
            putValue(MNEMONIC_KEY, KeyEvent.VK_D);
            this.setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            historyTable.requestFocusInWindow(); //always transfer focus
            int[] rows = historyTable.getSelectedRows();
            if (rows.length <= 0) {
                return;
            }
            int result = JOptionPane.showOptionDialog(HistoryFrame.this,
                    "Opravdu z historie odstranit " +
                    "všechny označené zprávy?", null,
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                    options, cancelOption);
            if (result < 0 || !options[result].equals(deleteOption)) {
                return;
            }
            //confirmed, let's delete it
            ArrayList<History.Record> histToDelete = new ArrayList<History.Record>();
            for (int i : rows) {
                i = historyTable.getRowSorter().convertRowIndexToModel(i);
                histToDelete.add(history.getRecord(i));
            }
            history.removeRecords(histToDelete);
            //refresh table
            historyTableModel.fireTableDataChanged();
            historyTable.getSelectionModel().clearSelection();
        }
    }

    /** Resend chosen sms from history */
    private class ResendAction extends AbstractAction {

        public ResendAction() {
            super(null, new ImageIcon(HistoryFrame.class.getResource(RES + "send-22.png")));
            this.putValue(SHORT_DESCRIPTION, "Přeposlat zprávu někomu jinému (Alt+S)");
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
            this.setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedHistory == null) {
                return;
            }
            //fire event and close
            actionSupport.fireActionPerformed(ACTION_RESEND_SMS, null);
            closeButton.doClick(0);
        }
    }

    /** Listener for history changes */
    private class HistoryActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            historyTableModel.fireTableDataChanged();
            historyTable.getSelectionModel().clearSelection();
        }
    }
    
    /** Table model for showing sms history */
    private class HistoryTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return history.getRecords().size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            History.Record record = history.getRecord(rowIndex);
            switch (columnIndex) {
                case 0:
                    return record.getDate();
                case 1:
                    String name = record.getName();
                    return name != null && !name.equals("") ? name : record.getNumber();
                case 2:
                    return record.getText().replaceAll("\n+", " "); //show spaces instead of linebreaks
                default:
                    logger.warning("Index out of bounds!");
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Datum";
                case 1:
                    return "Příjemce";
                case 2:
                    return "Text";
                default:
                    logger.warning("Index out of bounds!");
                    return null;
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Date.class;
                case 1:
                case 2:
                    return String.class;
                default:
                    logger.warning("Index out of bounds!");
                    return Object.class;
            }
        }

        @Override
        public void fireTableDataChanged() {
            super.fireTableDataChanged();
        }

        @Override
        public void fireTableRowsDeleted(int firstRow, int lastRow) {
            super.fireTableRowsDeleted(firstRow, lastRow);
        }

        @Override
        public void fireTableRowsInserted(int firstRow, int lastRow) {
            super.fireTableRowsInserted(firstRow, lastRow);
        }
    }

    /** Listener for changes in history table */
    private class HistoryTableListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int index = historyTable.getSelectedRow();
            boolean selected = (index >= 0);
            deleteAction.setEnabled(selected);
            resendAction.setEnabled(selected);
            if (!selected) {
                return;
            }
            index = historyTable.getRowSorter().convertRowIndexToModel(index);

            History.Record record = history.getRecord(index);
            dateLabel.setText(df.format(record.getDate()));
            nameLabel.setText(record.getName());
            numberLabel.setText(record.getNumber());
            operatorLabel.setText(record.getOperator());
            senderNameLabel.setText(record.getSenderName());
            senderNumberLabel.setText(record.getSenderNumber());
            textArea.setText(record.getText());
            textArea.setCaretPosition(0);

            selectedHistory = record;
        }
    }

    /** Renderer for date columns in history table */
    private class TableDateRenderer extends DefaultTableCellRenderer {

        private final ImageIcon icon = new ImageIcon(HistoryFrame.class.getResource(RES + "message-16.png"));

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            JLabel label = (JLabel) comp;
            label.setText(df.format(value));
            label.setIcon(icon);
            return label;
        }
    }
    
    /** Filter for searching in history table */
    private class HistoryRowFilter extends RowFilter<HistoryTableModel, Integer> {
        private Timer timer = new Timer(500, new ActionListener() { //updating after each event is slow,
            @Override
            public void actionPerformed(ActionEvent e) {            //therefore there is timer
                historyTableSorter.setRowFilter(HistoryRowFilter.this);
            }
        });
        public HistoryRowFilter() {
            timer.setRepeats(false);
        }
        @Override
        public boolean include(Entry<? extends HistoryTableModel, ? extends Integer> entry) {
            History.Record record = history.getRecord(entry.getIdentifier());
            String pattern = searchField.getText().toLowerCase();
            //search through text, name, number and date
            if (record.getText() != null && record.getText().toLowerCase().contains(pattern)) {
                return true;
            }
            if (record.getNumber() != null && record.getNumber().toLowerCase().contains(pattern)) {
                return true;
            }
            if (record.getName() != null && record.getName().toLowerCase().contains(pattern)) {
                return true;
            }
            if (record.getDate() != null && df.format(record.getDate()).toLowerCase().contains(pattern)) {
                return true;
            }
            return false;
        }
        /** request history search filter to be updated */
        public void requestUpdate() {
            timer.restart();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JLabel operatorLabel;
    private javax.swing.JButton resendButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel senderNameLabel;
    private javax.swing.JLabel senderNumberLabel;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
