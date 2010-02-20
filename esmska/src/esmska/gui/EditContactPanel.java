/*
 * EditContactPanel.java
 *
 * Created on 26. červenec 2007, 11:50
 */

package esmska.gui;

import esmska.data.Config;
import esmska.data.Contact;
import esmska.data.CountryPrefix;
import esmska.data.Keyring;
import esmska.data.Links;
import esmska.data.Operator;
import esmska.data.event.AbstractDocumentListener;
import esmska.utils.L10N;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import org.apache.commons.lang.StringUtils;
import org.openide.awt.Mnemonics;

/** Add new or edit current contact
 *
 * @author  ripper
 */
public class EditContactPanel extends javax.swing.JPanel {
    private static final ResourceBundle l10n = L10N.l10nBundle;
    private static final Border fieldBorder = new JTextField().getBorder();
    private static final Border lineRedBorder = BorderFactory.createLineBorder(Color.RED);
    
    private Config config = Config.getInstance();
    private Keyring keyring = Keyring.getInstance();
    private boolean multiMode; //edit multiple contacts
    private boolean userSet; //whether operator was set by user or by program

    private Action suggestOperatorAction;

    /**
     * Creates new form EditContactPanel
     */
    public EditContactPanel() {
        initComponents();
        //if not Substance LaF, add clipboard popup menu to text components
        if (!config.getLookAndFeel().equals(ThemeManager.LAF.SUBSTANCE)) {
            ClipboardPopupMenu.register(nameTextField);
            ClipboardPopupMenu.register(numberTextField);
        }
        
        //set up button for suggesting operator
        suggestOperatorAction = Actions.getSuggestOperatorAction(operatorComboBox, numberTextField);
        suggestOperatorButton.setAction(suggestOperatorAction);

        //listen for changes in number and guess operator
        numberTextField.getDocument().addDocumentListener(new AbstractDocumentListener() {
            @Override
            public void onUpdate(DocumentEvent e) {
                if (!userSet) {
                    operatorComboBox.selectSuggestedOperator(numberTextField.getText());
                    userSet = false;
                }
                updateCountryInfoLabel();
            }
        });

        //update components
        operatorComboBoxItemStateChanged(null);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameTextField = new JTextField();
        nameTextField.requestFocusInWindow();
        numberTextField = new JTextField() {
            @Override
            public String getText() {
                String text = super.getText();
                if (!text.startsWith("+"))
                text = config.getCountryPrefix() + text;
                return text;
            }
        }
        ;
        operatorComboBox = new OperatorComboBox();
        nameLabel = new JLabel();
        numberLabel = new JLabel();
        gatewayLabel = new JLabel();
        suggestOperatorButton = new JButton();
        jPanel1 = new JPanel();
        countryInfoLabel = new InfoLabel();
        credentialsInfoLabel = new InfoLabel();

        nameTextField.setToolTipText(l10n.getString("EditContactPanel.nameTextField.toolTipText")); // NOI18N
        nameTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });

        numberTextField.setColumns(13);
        numberTextField.setToolTipText(l10n.getString("EditContactPanel.numberTextField.toolTipText")); // NOI18N
        numberTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                numberTextFieldFocusLost(evt);
            }
        });

        operatorComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                operatorComboBoxItemStateChanged(evt);
            }
        });

        nameLabel.setLabelFor(nameTextField);
        Mnemonics.setLocalizedText(nameLabel, l10n.getString("EditContactPanel.nameLabel.text")); // NOI18N
        nameLabel.setToolTipText(nameTextField.getToolTipText());

        numberLabel.setLabelFor(numberTextField);
        Mnemonics.setLocalizedText(numberLabel, l10n.getString("EditContactPanel.numberLabel.text")); // NOI18N
        numberLabel.setToolTipText(numberTextField.getToolTipText());

        gatewayLabel.setLabelFor(operatorComboBox);
        Mnemonics.setLocalizedText(gatewayLabel, l10n.getString("EditContactPanel.gatewayLabel.text")); // NOI18N
        gatewayLabel.setToolTipText(operatorComboBox.getToolTipText());

        suggestOperatorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                suggestOperatorButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(countryInfoLabel, l10n.getString("EditContactPanel.countryInfoLabel.text")); // NOI18N
        countryInfoLabel.setVisible(false);
        Mnemonics.setLocalizedText(credentialsInfoLabel,l10n.getString(
            "EditContactPanel.credentialsInfoLabel.text"));
        credentialsInfoLabel.setText(MessageFormat.format(
            l10n.getString("EditContactPanel.credentialsInfoLabel.text"), Links.CONFIG_CREDENTIALS));
    credentialsInfoLabel.setVisible(false);

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(Alignment.LEADING)
        .addComponent(credentialsInfoLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        .addComponent(countryInfoLabel)
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(credentialsInfoLabel)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(countryInfoLabel))
    );

        GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(numberLabel)
                        .addComponent(nameLabel)
                        .addComponent(gatewayLabel))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(operatorComboBox, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(suggestOperatorButton))
                        .addComponent(nameTextField, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                        .addComponent(numberTextField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))))
            .addContainerGap())
    );

    layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {gatewayLabel, nameLabel, numberLabel});

    layout.setVerticalGroup(
        layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(nameLabel)
                .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(numberLabel)
                .addComponent(numberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(gatewayLabel)
                    .addComponent(operatorComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addComponent(suggestOperatorButton))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(74, Short.MAX_VALUE))
    );

    layout.linkSize(SwingConstants.VERTICAL, new Component[] {nameTextField, numberTextField});

    }// </editor-fold>//GEN-END:initComponents
    
    /** Check if the form is valid */
    public boolean validateForm() {
        boolean valid = true;
        boolean focusTransfered = false;
        JComponent[] comps;
        if (multiMode) {
            comps = new JComponent[] {};
        } else {
            comps = new JComponent[] {nameTextField, numberTextField};
        }
        for (JComponent c : comps) {
            valid = checkValid(c) && valid;
            if (!valid && !focusTransfered) {
                c.requestFocusInWindow();
                focusTransfered = true;
            }
        }
        return valid;
    }
    
    /** checks if component's content is valid */
    private boolean checkValid(JComponent c) {
        boolean valid = true;
        if (c == nameTextField) {
            valid = StringUtils.isNotEmpty(nameTextField.getText());
            updateBorder(c, valid);
        } else if (c == numberTextField) {
            valid = Contact.isValidNumber(numberTextField.getText());
            updateBorder(c, valid);
        }
        return valid;
    }
    
    /** sets highlighted border on non-valid components and regular border on valid ones */
    private void updateBorder(JComponent c, boolean valid) {
        if (valid) {
            c.setBorder(fieldBorder);
        } else {
            c.setBorder(lineRedBorder);
        }
    }

    /** Enable or disable multi-editing mode */
    private void setMultiMode(boolean multiMode) {
        this.multiMode = multiMode;

        nameTextField.setEnabled(!multiMode);
        numberTextField.setEnabled(!multiMode);
        suggestOperatorAction.setEnabled(!multiMode);
    }

    /** Show warning if user selected gateway which does not send
     * messages to country from which is the number
     */
    private void updateCountryInfoLabel() {
        countryInfoLabel.setVisible(false);

        //ensure that fields are sufficiently filled in
        Operator operator = operatorComboBox.getSelectedOperator();
        String number = numberTextField.getText();
        if (operator == null || !Contact.isValidNumber(number)) {
            return;
        }
        String prefix = CountryPrefix.extractCountryPrefix(number);
        String numberCountryCode = CountryPrefix.getCountryCode(prefix);
        if (StringUtils.isEmpty(numberCountryCode)) {
            return;
        }
        
        //skip on INT gateways
        String gwCountryCode = CountryPrefix.extractCountryCode(operator.getName());
        if (gwCountryCode == null || CountryPrefix.INTERNATIONAL_CODE.equals(gwCountryCode)) {
            return;
        }

        if (!numberCountryCode.equals(gwCountryCode)) {
            String text = MessageFormat.format(l10n.getString("EditContactPanel.countryInfoLabel.text"),
                    gwCountryCode, numberCountryCode);
            countryInfoLabel.setText(text);
            countryInfoLabel.setVisible(true);
        }
    }
    
    private void nameTextFieldFocusLost(FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
        checkValid(nameTextField);
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void numberTextFieldFocusLost(FocusEvent evt) {//GEN-FIRST:event_numberTextFieldFocusLost
        checkValid(numberTextField);
    }//GEN-LAST:event_numberTextFieldFocusLost

    private void suggestOperatorButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_suggestOperatorButtonActionPerformed
        userSet = false;
    }//GEN-LAST:event_suggestOperatorButtonActionPerformed

    private void operatorComboBoxItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_operatorComboBoxItemStateChanged
        userSet = true;

        //show warning if user selected gateway requiring registration
        //and no credentials are filled in
        Operator operator = operatorComboBox.getSelectedOperator();
        if (operator != null && operator.isLoginRequired() &&
                keyring.getKey(operator.getName()) == null) {
            credentialsInfoLabel.setVisible(true);
        } else {
            credentialsInfoLabel.setVisible(false);
        }

        //also update countryInfoLabel
        updateCountryInfoLabel();
    }//GEN-LAST:event_operatorComboBoxItemStateChanged
    
    /** Set contact to be edited or use null for new one */
    public void setContact(Contact contact) {
        setMultiMode(false);
        if (contact == null) {
            nameTextField.setText(null);
            numberTextField.setText(config.getCountryPrefix());
            operatorComboBox.selectSuggestedOperator(numberTextField.getText());
        } else {
            nameTextField.setText(contact.getName());
            numberTextField.setText(contact.getNumber());
            operatorComboBox.setSelectedOperator(contact.getOperator());
        }
        userSet = false;
    }

    /** Set contacts for collective editing. May not be null. */
    public void setContacts(Collection<Contact> contacts) {
        if (contacts.size() <= 1) {
            setContact(contacts.size() <= 0 ? null : contacts.iterator().next());
            return;
        }
        setMultiMode(true);
        operatorComboBox.setSelectedOperator(contacts.iterator().next().getOperator());
    }
    
    /** Get currently edited contact */
    public Contact getContact() {
        String name = nameTextField.getText();
        String number = numberTextField.getText();
        String operator = operatorComboBox.getSelectedOperatorName();
        
        if (!multiMode && (StringUtils.isEmpty(name) || StringUtils.isEmpty(number) ||
                StringUtils.isEmpty(operator))) {
            return null;
        } else {
            return new Contact(name, number, operator);
        }
    }

    /** Improve focus etc. before displaying panel */
    public void prepareForShow() {
        //no operator, try to suggest one
        if (operatorComboBox.getSelectedOperator() == null) {
            suggestOperatorAction.actionPerformed(null);
        }

        //give focus
        if (multiMode) {
            operatorComboBox.requestFocusInWindow();
        } else {
            nameTextField.requestFocusInWindow();
            nameTextField.selectAll();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private InfoLabel countryInfoLabel;
    private InfoLabel credentialsInfoLabel;
    private JLabel gatewayLabel;
    private JPanel jPanel1;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel numberLabel;
    private JTextField numberTextField;
    private OperatorComboBox operatorComboBox;
    private JButton suggestOperatorButton;
    // End of variables declaration//GEN-END:variables
    
}
