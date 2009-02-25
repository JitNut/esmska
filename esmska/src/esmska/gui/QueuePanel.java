/*
 * QueuePanel.java
 *
 * Created on 3. říjen 2007, 22:05
 */

package esmska.gui;

import esmska.ThemeManager;
import esmska.data.Queue.Events;
import esmska.data.event.ValuedEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jvnet.substance.SubstanceLookAndFeel;

import esmska.data.Icons;
import esmska.data.Queue;
import esmska.data.SMS;
import esmska.operators.Operator;
import esmska.operators.OperatorUtil;
import esmska.data.event.AbstractListDataListener;
import esmska.data.event.ActionEventSupport;
import esmska.utils.L10N;
import esmska.data.event.ValuedListener;
import esmska.utils.Workarounds;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.jvnet.substance.api.ColorSchemeAssociationKind;
import org.jvnet.substance.api.ComponentState;
import org.jvnet.substance.api.SubstanceColorScheme;
import org.jvnet.substance.api.renderers.SubstanceDefaultListCellRenderer;
import org.jvnet.substance.painter.highlight.SubstanceHighlightPainter;
import org.jvnet.substance.skin.SkinChangeListener;

/** SMS queue panel
 *
 * @author  ripper
 */
public class QueuePanel extends javax.swing.JPanel {
    /** A message was requested to be edited */
    public static final int ACTION_REQUEST_EDIT_SMS = 0;

    private static final Logger logger = Logger.getLogger(QueuePanel.class.getName());
    private static final String RES = "/esmska/resources/";
    private static final ResourceBundle l10n = L10N.l10nBundle;
    private static final Queue queue = Queue.getInstance();
    
    private Action deleteSMSAction = new DeleteSMSAction();
    private Action editSMSAction = new EditSMSAction();
    private Action smsUpAction = new SMSUpAction();
    private Action smsDownAction = new SMSDownAction();
    
    private QueueListModel queueListModel = new QueueListModel();
    //sms which have been requested to be edited
    private SMS editRequestedSMS;
    private QueuePopupMenu popup = new QueuePopupMenu();
    private QueueMouseListener mouseListener;
    //regularly update sms delay indicators
    private Timer timer = new Timer(500, new DelayListener());
    
    // <editor-fold defaultstate="collapsed" desc="ActionEvent support">
    private ActionEventSupport actionSupport = new ActionEventSupport(this);
    public void addActionListener(ActionListener actionListener) {
        actionSupport.addActionListener(actionListener);
    }
    public void removeActionListener(ActionListener actionListener) {
        actionSupport.removeActionListener(actionListener);
    }
    // </editor-fold>
    
    /** Creates new form QueuePanel */
    public QueuePanel() {
        initComponents();
        
        //add mouse listeners to the queue list
        mouseListener = new QueueMouseListener(queueList, popup);
        queueList.addMouseListener(mouseListener);

        //when model is changed update actions
        queueListModel.addListDataListener(new AbstractListDataListener() {
            @Override
            public void onUpdate(ListDataEvent e) {
                queueListValueChanged(null);
            }
        });
    }
    
    /** Get SMS which was requested to be edited */
    public SMS getEditRequestedSMS() {
        return editRequestedSMS;
    }
    
    /** Convert integer message delay to more human readable string delay.
     * @param delay number of milliseconds of the delay
     * @return human readable string of the delay, eg: "3h 15m 47s"
     */
    private String convertDelayToHumanString(long delay) {
        delay = Math.round(delay / 1000.0);
        long seconds = delay % 60;
        long minutes = (delay / 60) % 60;
        long hours = delay / 3600;
        
        StringBuilder builder = new StringBuilder();
        builder.append(seconds);
        builder.append(l10n.getString("QueuePanel.second_shortcut"));
        if (minutes > 0) {
            builder.insert(0, l10n.getString("QueuePanel.minute_shortcut") + " ");
            builder.insert(0, minutes);
        }
        if (hours > 0) {
            builder.insert(0, l10n.getString("QueuePanel.hour_shortcut") + " ");
            builder.insert(0, hours);
        }
        
        return builder.toString();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        smsUpButton = new JButton();
        smsDownButton = new JButton();
        jScrollPane2 = new JScrollPane();
        queueList = new JList();
        editButton = new JButton();
        deleteButton = new JButton();
        pauseButton = new JToggleButton();

        setBorder(BorderFactory.createTitledBorder(l10n.getString("QueuePanel.border.title"))); // NOI18N
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        smsUpButton.setAction(smsUpAction);
        smsUpButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
        smsUpButton.setText("");

        smsDownButton.setAction(smsDownAction);
        smsDownButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
        smsDownButton.setText("");

        queueList.setModel(queueListModel);
        queueList.setCellRenderer(new SMSQueueListRenderer(queueList));
        queueList.setVisibleRowCount(4);
        queueList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                queueListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(queueList);

        editButton.setAction(editSMSAction);
        editButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
        editButton.setText("");

        deleteButton.setAction(deleteSMSAction);
        deleteButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
        deleteButton.setText("");

        pauseButton.setAction(Actions.getQueuePauseAction(false));
        pauseButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
        pauseButton.setText(null);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                    .addComponent(smsDownButton)
                    .addComponent(smsUpButton))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deleteButton)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(pauseButton))
                    .addComponent(editButton))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {deleteButton, editButton, pauseButton, smsDownButton, smsUpButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pauseButton))
                    .addGroup(Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(smsUpButton)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(smsDownButton))
                    .addGroup(Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(editButton)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {deleteButton, editButton, pauseButton, smsDownButton, smsUpButton});

    }// </editor-fold>//GEN-END:initComponents
    
    private void queueListValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_queueListValueChanged
        if (evt != null && evt.getValueIsAdjusting()) {
            return;
        }
        if (queueList.getSelectedIndex() >= queueListModel.getSize()) {
            //selection model has not yet been updated
            return;
        }

        //update form components
        int size = queueList.getSelectedIndices().length;
        deleteSMSAction.setEnabled(size > 0);
        editSMSAction.setEnabled(size == 1);
        
        SMS sms = (SMS) queueList.getSelectedValue();
        if (sms != null && size == 1) {
            List<SMS> list = queue.getAll(sms.getOperator());
            int index = list.indexOf(sms);
            smsUpAction.setEnabled(index != 0);
            smsDownAction.setEnabled(index < list.size() - 1);
        } else {
            smsUpAction.setEnabled(false);
            smsDownAction.setEnabled(false);
        }
}//GEN-LAST:event_queueListValueChanged

    private void formFocusGained(FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        pauseButton.requestFocusInWindow();
    }//GEN-LAST:event_formFocusGained
    
    /** Erase sms from queue list */
    private class DeleteSMSAction extends AbstractAction {
        public DeleteSMSAction() {
            super(l10n.getString("Delete_messages"), 
                    new ImageIcon(QueuePanel.class.getResource(RES + "delete-16.png")));
            this.putValue(SHORT_DESCRIPTION,l10n.getString("Delete_selected_messages"));
            this.putValue(LARGE_ICON_KEY,
                    new ImageIcon(QueuePanel.class.getResource(RES + "delete-22.png")));
            this.setEnabled(false);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] smsArray = queueList.getSelectedValues();
            for (Object o : smsArray) {
                SMS sms = (SMS) o;
                queue.remove(sms);
            }

            //transfer focus
            if (queueListModel.getSize() > 0) {
                queueList.requestFocusInWindow();
            } else {
                pauseButton.requestFocusInWindow();
            }
        }
    }
    
    /** Edit sms from queue */
    private class EditSMSAction extends AbstractAction {
        public EditSMSAction() {
            super(l10n.getString("Edit_message"),
                    new ImageIcon(QueuePanel.class.getResource(RES + "edit-16.png")));
            this.putValue(SHORT_DESCRIPTION,l10n.getString("Edit_selected_message"));
            this.putValue(LARGE_ICON_KEY,
                    new ImageIcon(QueuePanel.class.getResource(RES + "edit-22.png")));
            this.setEnabled(false);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            SMS sms = (SMS) queueList.getSelectedValue();
            if (sms == null) {
                return;
            }
            
            editRequestedSMS = sms;
            queue.remove(sms);
            
            //fire event
            logger.fine("SMS requested for editing: " + sms.toDebugString());
            actionSupport.fireActionPerformed(ACTION_REQUEST_EDIT_SMS, null);
        }
    }
    
    /** move sms up in sms queue */
    private class SMSUpAction extends AbstractAction {
        public SMSUpAction() {
            super(l10n.getString("Move_up"),
                    new ImageIcon(QueuePanel.class.getResource(RES + "up-16.png")));
            this.putValue(SHORT_DESCRIPTION,l10n.getString("QueuePanel.Move_sms_up_in_the_queue"));
            this.putValue(LARGE_ICON_KEY,
                    new ImageIcon(QueuePanel.class.getResource(RES + "up-22.png")));
            this.setEnabled(false);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            SMS sms = (SMS) queueList.getSelectedValue();
            if (sms == null) {
                return;
            }
            queue.movePosition(sms, -1);
            int index = queueListModel.indexOf(sms);
            queueList.setSelectedIndex(index);
            queueList.ensureIndexIsVisible(index);
        }
    }
    
    /** move sms down in sms queue */
    private class SMSDownAction extends AbstractAction {
        public SMSDownAction() {
            super(l10n.getString("Move_down"),
                    new ImageIcon(QueuePanel.class.getResource(RES + "down-16.png")));
            this.putValue(SHORT_DESCRIPTION,l10n.getString("QueuePanel.Move_sms_down_in_the_queue"));
            this.putValue(LARGE_ICON_KEY,
                    new ImageIcon(QueuePanel.class.getResource(RES + "down-22.png")));
            this.setEnabled(false);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            SMS sms = (SMS) queueList.getSelectedValue();
            if (sms == null) {
                return;
            }
            queue.movePosition(sms, 1);
            int index = queueListModel.indexOf(sms);
            queueList.setSelectedIndex(index);
            queueList.ensureIndexIsVisible(index);
        }
    }
    
    /** Model for SMSQueueList */
    private class QueueListModel extends AbstractListModel {
        private int oldSize = getSize();

        public QueueListModel() {
            //listen for changes in contacts and fire events accordingly
            queue.addValuedListener(new ValuedListener<Events, SMS>() {
                @Override
                public void eventOccured(ValuedEvent<Events, SMS> e) {
                    switch (e.getEvent()) {
                        case SMS_ADDED:
                        case SENDING_SMS:
                        case SMS_SENDING_FAILED:
                        case SMS_POSITION_CHANGED:
                            fireContentsChanged(QueueListModel.this, 0, getSize());
                            break;
                        case SMS_REMOVED:
                        case QUEUE_CLEARED:
                            fireIntervalRemoved(QueueListModel.this, 0, oldSize);
                            break;
                    }
                    oldSize = getSize();
                    timer.start(); //start counting down delays
                }
            });
        }

        @Override
        public SMS getElementAt(int index) {
            return queue.getAll().get(index);
        }
        @Override
        public int getSize() {
            return queue.size();
        }
        public int indexOf(SMS element) {
            return queue.getAll().indexOf(element);
        }
        public void fireContentsChanged(int index0, int index1) {
            super.fireContentsChanged(this, index0, index1);
        }

    }
    
    /** Renderer for items in queue list */
    private class SMSQueueListRenderer extends SubstanceDefaultListCellRenderer {
        private final ListCellRenderer lafRenderer = new JList().getCellRenderer();
        private final JLabel delayLabel = new JLabel("", SwingConstants.TRAILING);
        private final ImageIcon sendIcon = new ImageIcon(getClass().getResource(RES + "send-16.png"));
        private final URL messageIconURI = getClass().getResource(RES + "message-32.png");
        private final boolean isSubstance = ThemeManager.isSubstanceCurrentLaF();
        private boolean selected = false; //whether current item is selected
        private final JList jlist; //list to render
        private SubstanceColorScheme scheme; //current Substance color scheme for this list
        private SubstanceColorScheme borderScheme; //current Substance border color scheme for this list
        private SubstanceHighlightPainter painter; //current Substance highlight painter for this list

        private final JPanel panel = new JPanel(new BorderLayout()) { //panel to wrap multiple labels
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //Substance is not painting highlights on JPanels, therefore we must
                //handle this painting on our own
                if (isSubstance && selected) {
                    painter.paintHighlight((Graphics2D) g, this, getWidth(),
                            getHeight(), 1f, null, scheme, scheme,
                            borderScheme, borderScheme, 0f);
                }
            }
        };

        public SMSQueueListRenderer(JList list) {
            this.jlist = list;
            panel.add(delayLabel, BorderLayout.LINE_END);
            delayLabel.setOpaque(true);
            
            updateSubstanceSkinValues();
            SubstanceLookAndFeel.registerSkinChangeListener(new SkinChangeListener() {
                @Override
                public void skinChanged() {
                    updateSubstanceSkinValues();
                }
            });
        }
        
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = lafRenderer.getListCellRendererComponent(list, value, index,
                    isSelected, false); //looks better without cell focus
            selected = isSelected;
            JLabel label = (JLabel) c;
            SMS sms = (SMS)value;
            
            //set text
            label.setText(StringUtils.defaultIfEmpty(sms.getName(),
                    OperatorUtil.stripCountryPrefix(sms.getNumber())));
            //problematic sms colored
            if ((sms.isProblematic()) && !isSelected) {
                label.setBackground(Color.RED);
            }
            //set colors on other components
            delayLabel.setForeground(label.getForeground());
            delayLabel.setBackground(label.getBackground());
            panel.setBackground(label.getBackground());
            //add operator logo
            Operator operator = OperatorUtil.getOperator(sms.getOperator());
            label.setIcon(operator != null ? operator.getIcon() : Icons.OPERATOR_BLANK);
            //set tooltip
            String text = WordUtils.wrap(sms.getText(), 50, null, true);
            text = Workarounds.escapeHtml(text);
            text = text.replaceAll("\n", "<br>");
            String tooltip = "<html><table><tr><td><img src=\"" + messageIconURI +
                    "\"></td><td valign=top><b>" + label.getText() + "</b><br>" +
                    (StringUtils.isEmpty(sms.getName())?"":OperatorUtil.stripCountryPrefix(sms.getNumber())+", ") +
                    sms.getOperator() + "<br><br>" + text +
                    "</td></tr></table></html>";
            panel.setToolTipText(tooltip);
            //set delay label
            if (queue.getAllWithStatus(SMS.Status.SENDING).contains(sms)) {
                delayLabel.setIcon(sendIcon);
                delayLabel.setText(null);
            } else {
                delayLabel.setIcon(null);
                long delay = queue.getSMSDelay(sms);
                delayLabel.setText(convertDelayToHumanString(delay));
            }
            //add to panel
            panel.add(label, BorderLayout.CENTER);

            return panel;
        }
        /** on substance skin update reinitialize some properties */
        private void updateSubstanceSkinValues() {
            if (!isSubstance) {
                return;
            }
            scheme = SubstanceLookAndFeel.getCurrentSkin(jlist).getColorScheme(jlist, ComponentState.SELECTED);
            borderScheme = SubstanceLookAndFeel.getCurrentSkin(jlist).getColorScheme(jlist,
                    ColorSchemeAssociationKind.BORDER ,ComponentState.SELECTED);
            painter = SubstanceLookAndFeel.getCurrentSkin(jlist).getHighlightPainter();
        }
    }
    
    /** Regularly update the information about current message delays */
    private class DelayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean timerNeeded = false;

            for (int i = 0; i < queueListModel.getSize(); i++) {
                SMS sms = queueListModel.getElementAt(i);
                long delay = queue.getSMSDelay(sms);
                if (delay > 0) {
                    timerNeeded = true;
                    queueListModel.fireContentsChanged(i, i);
                }
            }

            if (!timerNeeded) {
                //when everything is on 0, no need for timer to run, let's stop it
                timer.stop();
            }
        }
    }

    /** Popup menu in the queue list */
    private class QueuePopupMenu extends JPopupMenu {

        public QueuePopupMenu() {
            JMenuItem menuItem = null;

            //edit sms action
            menuItem = new JMenuItem(editSMSAction);
            this.add(menuItem);

            //delete sms action
            menuItem = new JMenuItem(deleteSMSAction);
            this.add(menuItem);

            //move sms up action
            menuItem = new JMenuItem(smsUpAction);
            this.add(menuItem);

            //move sms down action
            menuItem = new JMenuItem(smsDownAction);
            this.add(menuItem);

            this.addSeparator();

            //queue pause action
            menuItem = new JMenuItem(Actions.getQueuePauseAction(true));
            this.add(menuItem);
        }
    }
    
    /** Mouse listener on the queue list */
    private class QueueMouseListener extends ListPopupMouseListener {

        public QueueMouseListener(JList list, JPopupMenu popup) {
            super(list, popup);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //transfer on left button doubleclick
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() > 1) {
                editButton.doClick();
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton deleteButton;
    private JButton editButton;
    private JScrollPane jScrollPane2;
    private JToggleButton pauseButton;
    private JList queueList;
    private JButton smsDownButton;
    private JButton smsUpButton;
    // End of variables declaration//GEN-END:variables
    
}
