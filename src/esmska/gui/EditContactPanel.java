/*
 * EditContactPanel.java
 *
 * Created on 26. červenec 2007, 11:50
 */

package esmska.gui;

import esmska.data.Config;
import esmska.data.Contact;
import esmska.data.Keyring;
import esmska.data.Links;
import esmska.data.Gateway;
import esmska.data.Gateways;
import esmska.data.event.AbstractDocumentListener;
import esmska.data.event.ActionEventSupport;
import esmska.utils.L10N;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import org.apache.commons.lang.StringUtils;
import org.openide.awt.Mnemonics;

/** Add new or edit current contact
 *
 * @author  ripper
 */
public class EditContactPanel extends javax.swing.JPanel {
    private static final ResourceBundle l10n = L10N.l10nBundle;
    
    private Config config = Config.getInstance();
    private Keyring keyring = Keyring.getInstance();
    private boolean multiMode; //edit multiple contacts
    private boolean userSet; //whether gateway was set by user or by program

    // <editor-fold defaultstate="collapsed" desc="ActionEvent support">
    private ActionEventSupport actionSupport = new ActionEventSupport(this);
    public void addActionListener(ActionListener actionListener) {
        actionSupport.addActionListener(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        actionSupport.removeActionListener(actionListener);
    }
    // </editor-fold>

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
        
        //listen for changes in number and guess gateway
        numberTextField.getDocument().addDocumentListener(new AbstractDocumentListener() {
            @Override
            public void onUpdate(DocumentEvent e) {
                boolean usrSet = userSet;
                if (!userSet) {
                    gatewayComboBox.selectSuggestedGateway(numberTextField.getText());
                }
                gatewayComboBox.setFilter(numberTextField.getText());
                userSet = usrSet;
                updateCountryInfoLabel();
                updateSuggestGatewayButton();
                EditContactPanel.this.revalidate();
            }
        });

        // on keyring update update credentialsInfoLabel
        keyring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCredentialsInfoLabel();
                EditContactPanel.this.revalidate();
            }
        });

        // when some info label is shown or hidden, update the frame size
        ComponentListener resizeListener = new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                askForResize();
            }
            @Override
            public void componentShown(ComponentEvent e) {
                askForResize();
            }
            private void askForResize() {
                actionSupport.fireActionPerformed(ActionEventSupport.ACTION_NEED_RESIZE, null);
            }
        };
        for (Component comp : infoPanel.getComponents()) {
            comp.addComponentListener(resizeListener);
        }

        //update components
        gatewayComboBoxItemStateChanged(null);
    }

    /** Show or hide suggest gateway button */
    private void updateSuggestGatewayButton() {
        ArrayList<Gateway> gws = Gateways.getInstance().suggestGateway(numberTextField.getText()).get1();
        boolean visible = false;
        if (gws.size() > 1) {
            visible = true;
        }
        if (gws.size() == 1 && gatewayComboBox.getSelectedGateway() != gws.get(0)) {
            visible = true;
        }
        suggestGatewayButton.setVisible(visible);
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
                if (StringUtils.isNotEmpty(text) && !text.startsWith("+"))
                text = config.getCountryPrefix() + text;
                return text;
            }
        }
        ;
        gatewayComboBox = new GatewayComboBox();
        nameLabel = new JLabel();
        numberLabel = new JLabel();
        gatewayLabel = new JLabel();
        suggestGatewayButton = new JButton();
        infoPanel = new JPanel();
        countryInfoLabel = new InfoLabel();
        credentialsInfoLabel = new InfoLabel();
        nameWarnLabel = new JLabel();
        numberWarnLabel = new JLabel();

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

        gatewayComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                gatewayComboBoxItemStateChanged(evt);
            }
        });

        nameLabel.setLabelFor(nameTextField);
        Mnemonics.setLocalizedText(nameLabel, l10n.getString("EditContactPanel.nameLabel.text")); // NOI18N
        nameLabel.setToolTipText(nameTextField.getToolTipText());

        numberLabel.setLabelFor(numberTextField);
        Mnemonics.setLocalizedText(numberLabel, l10n.getString("EditContactPanel.numberLabel.text")); // NOI18N
        numberLabel.setToolTipText(numberTextField.getToolTipText());

        gatewayLabel.setLabelFor(gatewayComboBox);
        Mnemonics.setLocalizedText(gatewayLabel, l10n.getString("EditContactPanel.gatewayLabel.text")); // NOI18N
        gatewayLabel.setToolTipText(gatewayComboBox.getToolTipText());

        suggestGatewayButton.setAction(Actions.getSuggestGatewayAction(gatewayComboBox, numberTextField));
        suggestGatewayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                suggestGatewayButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(countryInfoLabel, l10n.getString("EditContactPanel.countryInfoLabel.text")); // NOI18N
        countryInfoLabel.setVisible(false);
        Mnemonics.setLocalizedText(credentialsInfoLabel,l10n.getString(
            "EditContactPanel.credentialsInfoLabel.text"));
        credentialsInfoLabel.setText(MessageFormat.format(
            l10n.getString("EditContactPanel.credentialsInfoLabel.text"), Links.CONFIG_GATEWAYS));
    credentialsInfoLabel.setVisible(false);

        GroupLayout infoPanelLayout = new GroupLayout(infoPanel);
    infoPanel.setLayout(infoPanelLayout);
    infoPanelLayout.setHorizontalGroup(
        infoPanelLayout.createParallelGroup(Alignment.LEADING)
        .addComponent(credentialsInfoLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
        .addComponent(countryInfoLabel, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
    );
    infoPanelLayout.setVerticalGroup(
        infoPanelLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(infoPanelLayout.createSequentialGroup()
            .addComponent(credentialsInfoLabel)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(countryInfoLabel))
    );

    nameWarnLabel.setIcon(new ImageIcon(getClass().getResource("/esmska/resources/warning-16.png"))); // NOI18N
    nameWarnLabel.setToolTipText(nameTextField.getToolTipText());
    nameWarnLabel.setVisible(false);

    numberWarnLabel.setIcon(new ImageIcon(getClass().getResource("/esmska/resources/warning-16.png"))); // NOI18N
    numberWarnLabel.setToolTipText(numberTextField.getToolTipText());
    numberWarnLabel.setVisible(false);

        GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(infoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(numberLabel)
                        .addComponent(nameLabel)
                        .addComponent(gatewayLabel))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(gatewayComboBox, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(suggestGatewayButton))
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addComponent(numberTextField, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                .addComponent(nameTextField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addComponent(nameWarnLabel, Alignment.TRAILING)
                                .addComponent(numberWarnLabel, Alignment.TRAILING))))))
            .addContainerGap())
    );

    layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {gatewayLabel, nameLabel, numberLabel});

    layout.setVerticalGroup(
        layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addComponent(nameWarnLabel))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(numberLabel)
                    .addComponent(numberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addComponent(numberWarnLabel))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(gatewayLabel)
                    .addComponent(gatewayComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addComponent(suggestGatewayButton))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    layout.linkSize(SwingConstants.VERTICAL, new Component[] {nameTextField, nameWarnLabel, numberTextField, numberWarnLabel});

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
        revalidate();
        return valid;
    }
    
    /** checks if component's content is valid */
    private boolean checkValid(JComponent c) {
        boolean valid = true;
        if (c == nameTextField) {
            valid = StringUtils.isNotEmpty(nameTextField.getText());
            nameWarnLabel.setVisible(!valid);
        } else if (c == numberTextField) {
            valid = Contact.isValidNumber(numberTextField.getText());
            numberWarnLabel.setVisible(!valid);
        }
        return valid;
    }
    
    /** Enable or disable multi-editing mode */
    private void setMultiMode(boolean multiMode) {
        this.multiMode = multiMode;
        nameTextField.setEnabled(!multiMode);
        numberTextField.setEnabled(!multiMode);
        suggestGatewayButton.setVisible(!multiMode);
        revalidate();
    }

    /** Show warning if user selected gateway can't send messages to a recipient
     * number (based on supported prefixes list)
     */
    private void updateCountryInfoLabel() {
        countryInfoLabel.setVisible(false);

        //ensure that fields are sufficiently filled in
        Gateway gateway = gatewayComboBox.getSelectedGateway();
        String number = numberTextField.getText();
        if (gateway == null || !Contact.isValidNumber(number)) {
            return;
        }

        boolean supported = Gateways.isNumberSupported(gateway, number);
        if (!supported) {
            String text = MessageFormat.format(l10n.getString("EditContactPanel.countryInfoLabel.text"),
                    StringUtils.join(gateway.getSupportedPrefixes(), ','));
            countryInfoLabel.setText(text);
            countryInfoLabel.setVisible(true);
        }
    }

    /** Show warning if user selected gateway requiring registration
     * and no credentials are filled in
     */
    private void updateCredentialsInfoLabel() {
        Gateway gateway = gatewayComboBox.getSelectedGateway();
        if (gateway != null && gateway.isLoginRequired() &&
                keyring.getKey(gateway.getName()) == null) {
            credentialsInfoLabel.setVisible(true);
        } else {
            credentialsInfoLabel.setVisible(false);
        }
    }
    
    private void nameTextFieldFocusLost(FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
        checkValid(nameTextField);
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void numberTextFieldFocusLost(FocusEvent evt) {//GEN-FIRST:event_numberTextFieldFocusLost
        checkValid(numberTextField);
    }//GEN-LAST:event_numberTextFieldFocusLost

    private void suggestGatewayButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_suggestGatewayButtonActionPerformed
        userSet = false;
    }//GEN-LAST:event_suggestGatewayButtonActionPerformed

    private void gatewayComboBoxItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_gatewayComboBoxItemStateChanged
        userSet = (evt != null);

        updateCredentialsInfoLabel();
        updateCountryInfoLabel();
        updateSuggestGatewayButton();
        revalidate();
    }//GEN-LAST:event_gatewayComboBoxItemStateChanged
    
    /** Set contact to be edited or use null for new one */
    public void setContact(Contact contact) {
        setMultiMode(false);
        if (contact == null) {
            nameTextField.setText(null);
            numberTextField.setText(config.getCountryPrefix());
        } else {
            nameTextField.setText(contact.getName());
            numberTextField.setText(contact.getNumber());
            gatewayComboBox.setSelectedGateway(contact.getGateway());
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
        gatewayComboBox.setSelectedGateway(contacts.iterator().next().getGateway());
    }
    
    /** Get currently edited contact */
    public Contact getContact() {
        String name = nameTextField.getText();
        String number = numberTextField.getText();
        String gateway = gatewayComboBox.getSelectedGatewayName();
        
        if (!multiMode && (StringUtils.isEmpty(name) || StringUtils.isEmpty(number) ||
                StringUtils.isEmpty(gateway))) {
            return null;
        } else {
            return new Contact(name, number, gateway);
        }
    }

    /** Improve focus etc. before displaying panel */
    public void prepareForShow() {
        //give focus
        if (multiMode) {
            gatewayComboBox.requestFocusInWindow();
        } else {
            nameTextField.requestFocusInWindow();
            nameTextField.selectAll();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private InfoLabel countryInfoLabel;
    private InfoLabel credentialsInfoLabel;
    private GatewayComboBox gatewayComboBox;
    private JLabel gatewayLabel;
    private JPanel infoPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel nameWarnLabel;
    private JLabel numberLabel;
    private JTextField numberTextField;
    private JLabel numberWarnLabel;
    private JButton suggestGatewayButton;
    // End of variables declaration//GEN-END:variables
    
}
