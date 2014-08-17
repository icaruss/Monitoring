/*
 * 
 */

package settingsView;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;

import unix.ExecuteUnixOperations;
import unix.GuiParameterCheck;
import viewLogic.CSharedInstance;
import viewLogic.CViewConstants.MonitorType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import log.MonLogger;
import viewLogic.CViewConstants;

/**
 * The Class CSettingsController.
 */
public class CSettingsController implements Initializable {

	/** The btn connect. */
	@FXML
	// fx:id="btnConnect"
	private Button btnConnect; // Value injected by FXMLLoader

	/** The btn save configuration. */
	@FXML
	// fx:id="btnSaveConfiguration"
	private Button btnSaveConfiguration; // Value injected by FXMLLoader

	/** The chkbox clix. */
	@FXML
	// fx:id="chkboxClix"
	private RadioButton chkboxClix; // Value injected by FXMLLoader

	/** The chkbox mdisr. */
	@FXML
	// fx:id="chkboxMdisr"
	private RadioButton chkboxMdisr; // Value injected by FXMLLoader

	/** The chkbox mdsr. */
	@FXML
	// fx:id="chkboxMdsr"
	private RadioButton chkboxMdsr; // Value injected by FXMLLoader

	/** The chkbox mdssr. */
	@FXML
	// fx:id="chkboxMdssr"
	private RadioButton chkboxMdssr; // Value injected by FXMLLoader

	/** The cmb start selection. */
	@FXML
	// fx:id="cmbStartSelection"
	private ComboBox<String> cmbStartSelection; // Value injected by FXMLLoader

	/** The cmb id. */
	@FXML
	// fx:id="cmbID"
	private ComboBox<String> cmbID;

	/** The pane time frame. */
	@FXML
	// fx:id="paneTimeFrame"
	private AnchorPane paneTimeFrame; // Value injected by FXMLLoader

	/** The txt field host name. */
	@FXML
	// fx:id="txtFieldHostName"
	private TextField txtFieldHostName; // Value injected by FXMLLoader

	/** The txt field instance. */
	@FXML
	// fx:id="txtFieldInstance"
	private TextField txtFieldInstance; // Value injected by FXMLLoader

	/** The txt field instance time. */
	@FXML
	// fx:id="txtFieldInstanceTime"
	private TextField txtFieldInstanceTime; // Value injected by FXMLLoader

	/** The txt field password. */
	@FXML
	// fx:id="txtFieldPassword"
	private PasswordField txtFieldPassword; // Value injected by FXMLLoader

	/** The txt field time frame from. */
	@FXML
	// fx:id="txtFieldTimeFrameFrom"
	private TextField txtFieldTimeFrameFrom; // Value injected by FXMLLoader

	/** The txt field time frame to. */
	@FXML
	// fx:id="txtFieldTimeFrameTo"
	private TextField txtFieldTimeFrameTo; // Value injected by FXMLLoader

	/** The txt field user name. */
	@FXML
	// fx:id="txtFieldUserName"
	private TextField txtFieldUserName; // Value injected by FXMLLoader

	/** The txt field memory pop. */
	@FXML
	// fx:id="txtFieldMemoryPop"
	private TextField txtFieldMemoryPop; // Value injected by FXMLLoader

	/** The txt field port. */
	@FXML
	// fx:id="txtFieldPort"
	private TextField txtFieldPort; // Value injected by FXMLLoader

	/** The txt field configuration id. */
	@FXML
	// fx:id="txtFieldConfigurationID"
	private TextField txtFieldConfigurationID; // Value injected by FXMLLoader

	/** The lbl response to user. */
	@FXML
	// fx:id="lblResponseToUser"
	private Label lblResponseToUser; // Value injected by FXMLLoader

	/** The pnl settings. */
	@FXML
	// fx:id="pnlSettings"
	private AnchorPane pnlSettings; // Value injected by FXMLLoader

	/** The txt configuration id. */
	@FXML
	// fx:id="txtFieldTimeFrameTo"
	private Text txtConfigurationID;

	/** The port lbl. */
	@FXML
	// fx:id="portLbl"
	private Text portLbl;

	// ////// logic Variables //////

	// ////// end logic Variables ///

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert btnConnect != null : "fx:id=\"btnConnect\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert btnSaveConfiguration != null : "fx:id=\"btnSaveConfiguration\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert chkboxClix != null : "fx:id=\"chkboxClix\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert chkboxMdisr != null : "fx:id=\"chkboxMdisr\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert chkboxMdsr != null : "fx:id=\"chkboxMdsr\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert chkboxMdssr != null : "fx:id=\"chkboxMdssr\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert cmbStartSelection != null : "fx:id=\"cmbStartSelection\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert paneTimeFrame != null : "fx:id=\"paneTimeFrame\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldHostName != null : "fx:id=\"txtFieldHostName\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldInstance != null : "fx:id=\"txtFieldInstance\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldInstanceTime != null : "fx:id=\"txtFieldInstanceTime\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldPassword != null : "fx:id=\"txtFieldPassword\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldTimeFrameFrom != null : "fx:id=\"txtFieldTimeFrameFrom\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldTimeFrameTo != null : "fx:id=\"txtFieldTimeFrameTo\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldUserName != null : "fx:id=\"txtFieldUserName\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldMemoryPop != null : "fx:id=\"txtFieldMemoryPop\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldPort != null : "fx:id=\"txtFieldPort\" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert txtFieldConfigurationID != null : "fx:id=\"txtFieldConfigurationID \" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert lblResponseToUser != null : "fx:id=\"lblResponseToUser \" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert pnlSettings != null : "fx:id=\"pnlSettings \" was not injected: check your FXML file 'Setting_Page.fxml'.";
		assert portLbl != null : "fx:id=\"portLbl \" was not injected: check your FXML file 'Setting_Page.fxml'.";

		// Initialize your logic here: all @FXML variables will have been
		// injected

		setComponentsEditable(true);

		// configuration ids added here
		Set<String> configurationsKeySet = CSharedInstance.getInstance()
				.getAllConfigurationKeys();
		if (configurationsKeySet != null && !configurationsKeySet.isEmpty()) {
			cmbID.getItems().addAll(configurationsKeySet);
		}

		btnConnect.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (updateLblIfInputNotOK()) {
					// start connection

					Map<String, Object> currentSettings = getCurrentSettingsOnView();

					// save configuration
					CSharedInstance.getInstance().saveConfigurations(
							currentSettings);

					if (cmbStartSelection.getSelectionModel()
							.getSelectedIndex() == 1) // TimeFrame
					{
						CSharedInstance
								.getInstance()
								.updateWaitingTimeForResults(
										(String) currentSettings
												.get(CViewConstants.START_FROM_TIME),
										(String) currentSettings
												.get(CViewConstants.START_TO_TIME));

					} else {
						CSharedInstance.getInstance().totalSecondsCountdown = -1;
					}

					CSharedInstance.getInstance().currentMonitoring = MonitorType.MonitorTypeElse;

					try {
						// TODO: Test the functionality

						CSharedInstance.getInstance().setNewExecuteUnixOp(
								currentSettings);

						ExecuteUnixOperations exUnixOp = CSharedInstance
								.getInstance().executeUnixOperations;

						if ((String) currentSettings.get(CViewConstants.START) == CViewConstants.START_IMMEDIATELY) {
							MonLogger.myLogger.log(Level.INFO, "S T A R T");
							MonLogger.myLogger.log(Level.INFO,
									"Program started immediately");
							exUnixOp.start();
						} else if ((String) currentSettings
								.get(CViewConstants.START) == CViewConstants.START_FRAME_TIME) {
							MonLogger.myLogger.log(Level.INFO, "S T A R T");
							MonLogger.myLogger.log(Level.INFO,
									"Program started on time");
							exUnixOp.startOnTime();

						}

						CSharedInstance.getInstance().isMonitoringDone = false;

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						Stage stage = (Stage) btnConnect.getScene().getWindow();
						stage.close();
					}

				}

			}

		});

		btnSaveConfiguration.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (updateLblIfInputNotOK()) {
					Map<String, Object> currentSettings = getCurrentSettingsOnView();

					// save configuration
					CSharedInstance.getInstance().saveConfigurations(
							currentSettings);

					// update view
					String key = (String) currentSettings
							.get(CViewConstants.CONFIGURATION_ID);

					if (cmbID.getItems().size() >= 10) {
						cmbID.getItems().remove(cmbID.getItems().size() - 1);
					}

					cmbID.getItems().add(1, key);
				}
			}

		});

		txtFieldHostName.textProperty().addListener(
				new ChangeListener<String>() {

					private boolean ignore;

					@Override
					public void changed(
							ObservableValue<? extends String> observableValue,
							String s, String s1) {
						if (ignore || s1 == null)
							return;

						if (maxLengthInText() < s1.length()) {
							ignore = true;
							txtFieldHostName.setText(s1.substring(0,
									maxLengthInText()));
							ignore = false;
						} else if (s1.length() > 0) {
							char c = s1.charAt(s1.length() - 1);

							if (!isLatinLetter(c) && !isDigit(c) && c != '.') {
								ignore = true;
								txtFieldHostName.setText(s);
								ignore = false;
							}

						}
					}

				});

		txtFieldInstance.textProperty().addListener(
				new ChangeListener<String>() {

					private boolean ignore;

					@Override
					public void changed(
							ObservableValue<? extends String> observableValue,
							String s, String s1) {
						if (ignore || s1 == null)
							return;

						if (maxLengthInText() < s1.length()) {
							ignore = true;
							txtFieldInstance.setText(s1.substring(0,
									maxLengthInText()));
							ignore = false;
						} else if (s1.length() > 0) {
							char c = s1.charAt(s1.length() - 1);

							if (!isLatinLetter(c) && !isDigit(c)) {
								ignore = true;
								txtFieldInstance.setText(s);
								ignore = false;
							}

						}
					}

				});

		txtFieldInstanceTime.textProperty().addListener(
				new ChangeListener<String>() {

					private boolean ignore;

					@Override
					public void changed(
							ObservableValue<? extends String> observableValue,
							String s, String s1) {
						if (ignore || s1 == null)
							return;

						if (s1.length() > 0) {
							char c = s1.charAt(s1.length() - 1);

							try {
								if (!isDigit(c)
										&& Integer.parseInt(s1) > maxAllowedInstanceInterval()) {
									ignore = true;
									txtFieldInstanceTime.setText(s);
									ignore = false;
								}
							} catch (NumberFormatException e) {
								ignore = true;
								txtFieldInstanceTime.setText(s);
								ignore = false;
							}

						}
					}

				});

		txtFieldMemoryPop.textProperty().addListener(
				new ChangeListener<String>() {

					private boolean ignore;

					@Override
					public void changed(
							ObservableValue<? extends String> observableValue,
							String s, String s1) {
						if (ignore || s1 == null)
							return;

						if (s1.length() > 0) {
							char c = s1.charAt(s1.length() - 1);

							boolean isAnySelected = chkboxMdsr.isSelected()
									|| chkboxMdisr.isSelected()
									|| chkboxMdssr.isSelected();

							if (!isDigit(c) || !isAnySelected) {
								ignore = true;
								txtFieldMemoryPop.setText(s);
								ignore = false;
							}

						}
					}

				});

		txtFieldPort.textProperty().addListener(new ChangeListener<String>() {

			private boolean ignore;

			@Override
			public void changed(
					ObservableValue<? extends String> observableValue,
					String s, String s1) {
				if (ignore || s1 == null)
					return;

				if (s1.length() > 0) {
					char c = s1.charAt(s1.length() - 1);

					if (!isDigit(c) || !chkboxClix.isSelected()) {
						ignore = true;
						txtFieldPort.setText(s);
						ignore = false;
					}

				}
			}

		});

		txtFieldTimeFrameFrom.textProperty().addListener(
				new ChangeListener<String>() {

					private boolean ignore;

					@Override
					public void changed(
							ObservableValue<? extends String> observableValue,
							String s, String s1) {
						if (ignore || s1 == null)
							return;

						if (s1.length() > 0) {

							if (!checkIfInputTimeFrameAcceptable(txtFieldTimeFrameFrom.getText())) {
								ignore = true;
								txtFieldTimeFrameFrom.setText(s);
								ignore = false;
							}

						}
					}

				});

		txtFieldTimeFrameTo.textProperty().addListener(
				new ChangeListener<String>() {

					private boolean ignore;

					@Override
					public void changed(ObservableValue<? extends String> observableValue,String s, String s1) 
					{
						if (ignore || s1 == null)
							return;
						
						if (s1.length() > 0)
						{
							if (!checkIfInputTimeFrameAcceptable(txtFieldTimeFrameTo.getText())) 
							{
								ignore = true;
								txtFieldTimeFrameTo.setText(s);
								ignore = false;
							}

						}
					}

				});

		txtFieldConfigurationID.textProperty().addListener(
				new ChangeListener<String>() {

					private boolean ignore;

					@Override
					public void changed(
							ObservableValue<? extends String> observableValue,
							String s, String s1) {
						if (ignore || s1 == null)
							return;

						if (maxLengthInText() < s1.length()) {
							ignore = true;
							txtFieldConfigurationID.setText(s1.substring(0,
									maxLengthInText()));
							ignore = false;
						} else if (s1.length() > 0) {
							char c = s1.charAt(s1.length() - 1);

							if (!isLatinLetter(c) && !isDigit(c)) {
								ignore = true;
								txtFieldConfigurationID.setText(s);
								ignore = false;
							}

						}
					}

				});

	}

	// ///////////////////// Events Sections ///////////////////////////

	/**
	 * Chkbox mdsr on mouse clicked.
	 */
	public void chkboxMdsrOnMouseClicked() {
		if (chkboxMdsr.isSelected()) {
			chkboxMdsr.setSelected(true);
		} else {
			chkboxMdsr.setSelected(false);

			updateMemoryPop();
		}

	}

	/**
	 * Chkbox mdisr on mouse clicked.
	 */
	public void chkboxMdisrOnMouseClicked() {
		if (chkboxMdisr.isSelected()) {
			chkboxMdisr.setSelected(true);
		} else {
			chkboxMdisr.setSelected(false);

			updateMemoryPop();
		}

	}

	/**
	 * Chkbox mdssr on mouse clicked.
	 */
	public void chkboxMdssrOnMouseClicked() {
		if (chkboxMdssr.isSelected()) {
			chkboxMdssr.setSelected(true);
		} else {
			chkboxMdssr.setSelected(false);

			updateMemoryPop();
		}

	}

	/**
	 * Chk box clix on mouse pressed.
	 */
	public void chkBoxClixOnMousePressed() {
		if (chkboxClix.isSelected()) {
			chkboxClix.setSelected(true);
		} else {
			chkboxClix.setSelected(false);

			updatePort();
		}
	}

	/**
	 * Cmb selection on action.
	 */
	public void cmbSelectionOnAction() {
		int selectedIndex = cmbStartSelection.getSelectionModel()
				.getSelectedIndex();

		if (selectedIndex == 1) // Starting with 0 , with frame is in 2nd
								// location
		{
			paneTimeFrame.setVisible(true);
		} else {
			paneTimeFrame.setVisible(false);

			if (cmbID.getSelectionModel().getSelectedIndex() > 0) {
				Map<String, Object> map = CSharedInstance.getInstance()
						.getChosenConfiguration(
								cmbID.getSelectionModel().getSelectedItem());

				txtFieldTimeFrameFrom.setText((String) map
						.get(CViewConstants.START_FROM_TIME));
				txtFieldTimeFrameTo.setText((String) map
						.get(CViewConstants.START_TO_TIME));
			} else {
				txtFieldTimeFrameFrom.setText("");
				txtFieldTimeFrameTo.setText("");
			}
		}
	}

	/**
	 * Cmb select id on action.
	 */
	public void cmbSelectIDOnAction() {
		int selectedIndex = cmbID.getSelectionModel().getSelectedIndex();

		setComponentsEditable(true);

		// new Configuration
		if (selectedIndex == 0) {
			txtConfigurationID.setVisible(true);
			txtFieldConfigurationID.setVisible(true);

			clearSettingPage();
		} else if (selectedIndex > 0) // existing configuration was chosen
		{
			txtConfigurationID.setVisible(false);
			txtFieldConfigurationID.setVisible(false);

			setFieldsOnViewByConfigurationID(cmbID.getSelectionModel()
					.getSelectedItem());
		}

	}

	/**
	 * Clear setting page.
	 */
	private void clearSettingPage() {
		txtFieldHostName.clear();
		txtFieldUserName.clear();
		txtFieldPassword.clear();
		txtFieldInstance.clear();
		txtFieldInstanceTime.clear();
		chkboxMdsr.setSelected(false);
		chkboxMdisr.setSelected(false);
		chkboxMdssr.setSelected(false);
		txtFieldMemoryPop.clear();
		chkboxClix.setSelected(false);
		txtFieldPort.clear();
		cmbStartSelection.setPromptText(cmbStartSelection.getPromptText());
		paneTimeFrame.setVisible(false);
		txtFieldTimeFrameFrom.clear();
		txtFieldTimeFrameTo.clear();
	}

	/**
	 * Sets the components editable.
	 * 
	 * @param isEditable
	 *            the new components editable
	 */
	private void setComponentsEditable(boolean isEditable) {
		txtFieldHostName.setEditable(isEditable);
		txtFieldUserName.setEditable(isEditable);
		txtFieldPassword.setEditable(isEditable);
		txtFieldInstance.setEditable(isEditable);
		txtFieldInstanceTime.setEditable(isEditable);

		chkboxMdsr.visibleProperty().setValue(isEditable);
		chkboxMdisr.visibleProperty().setValue(isEditable);
		chkboxMdssr.visibleProperty().setValue(isEditable);
		chkboxClix.visibleProperty().setValue(isEditable);

		txtFieldMemoryPop.setEditable(isEditable);
		txtFieldPort.setEditable(isEditable);
		cmbStartSelection.setPromptText(cmbStartSelection.getPromptText());
		paneTimeFrame.setVisible(isEditable);
		txtFieldTimeFrameFrom.setEditable(isEditable);
		txtFieldTimeFrameTo.setEditable(isEditable);
	}

	/**
	 * Sets the fields on view by configuration id.
	 * 
	 * @param selectedItem
	 *            the new fields on view by configuration id
	 */
	private void setFieldsOnViewByConfigurationID(String selectedItem) {
		Map<String, Object> map = CSharedInstance.getInstance()
				.getChosenConfiguration(selectedItem);

		if (map == null || map.isEmpty()) {
			cmbID.getSelectionModel().select(0);
		} else {
			String hostName = (String) map.get(CViewConstants.HOSTNAME);
			if (hostName != null) {
				txtFieldHostName.setText(hostName);
			}

			String userName = (String) map.get(CViewConstants.USERNAME);
			if (userName != null) {
				txtFieldUserName.setText(userName);
			}

			String password = (String) map.get(CViewConstants.PASSWORD);
			if (password != null) {
				txtFieldPassword.setText(password);
			}

			String instance = (String) map.get(CViewConstants.INSTANCE);
			if (instance != null) {
				txtFieldInstance.setText(instance);
			}

			String interval = (String) map.get(CViewConstants.INTERVAL);
			if (interval != null) {
				txtFieldInstanceTime.setText(interval);
			}

			Boolean isMdsr = (Boolean) map.get(CViewConstants.MDSR);
			if (isMdsr != null) {
				chkboxMdsr.setSelected(isMdsr);
				;
			}

			Boolean isMdisr = (Boolean) map.get(CViewConstants.MDISR);
			if (isMdisr != null) {
				chkboxMdisr.setSelected(isMdisr);
				;
			}

			Boolean isMdssr = (Boolean) map.get(CViewConstants.MDSSR);
			if (isMdssr != null) {
				chkboxMdssr.setSelected(isMdssr);
				;
			}

			String memPop = (String) map.get(CViewConstants.MEMORY_POP);
			if (memPop != null) {
				txtFieldMemoryPop.setText(memPop);
			}

			Boolean isClix = (Boolean) map.get(CViewConstants.CLIX);
			if (isClix != null) {
				chkboxClix.setSelected(isClix);
				;
			}

			String port = (String) map.get(CViewConstants.PORT);
			if (port != null) {
				txtFieldPort.setText(port);
			}

			String selectedIndexSelectionString = (String) map
					.get(CViewConstants.START);

			if (selectedIndexSelectionString
					.equalsIgnoreCase(CViewConstants.START_IMMEDIATELY)) {
				cmbStartSelection.getSelectionModel().select(0);

				paneTimeFrame.setVisible(false);
			}

			else if (selectedIndexSelectionString
					.equalsIgnoreCase(CViewConstants.START_FRAME_TIME)) // time
																		// frame
																		// chosen
			{
				cmbStartSelection.getSelectionModel().select(1);

				paneTimeFrame.setVisible(true);

				String timeFrom = (String) map
						.get(CViewConstants.START_FROM_TIME);
				if (timeFrom != null) {
					txtFieldTimeFrameFrom.setText(timeFrom);
				}

				String timeTo = (String) map.get(CViewConstants.START_TO_TIME);
				if (timeTo != null) {
					txtFieldTimeFrameTo.setText(timeTo);
				}

			}

		}

	}

	// /////////////// LOGIC //////////////////////////

	/**
	 * Checks if is latin letter.
	 * 
	 * @param c
	 *            the c
	 * @return true, if is latin letter
	 */
	public static boolean isLatinLetter(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}

	/**
	 * Checks if is digit.
	 * 
	 * @param c
	 *            the c
	 * @return true, if is digit
	 */
	public static boolean isDigit(char c) {
		return (c >= '0' && c <= '9');
	}

	/**
	 * Max length in text.
	 * 
	 * @return the int
	 */
	public static int maxLengthInText() {
		return 30;
	}

	/**
	 * Max allowed instance interval.
	 * 
	 * @return the int
	 */
	public static int maxAllowedInstanceInterval() {
		return 1000000;
	}

	/**
	 * Update memory pop.
	 */
	private void updateMemoryPop() {
		boolean isSelected = chkboxMdssr.isSelected()
				|| chkboxMdisr.isSelected() || chkboxMdsr.isSelected();

		if (!isSelected) {
			txtFieldMemoryPop.setText("");
		}

	}

	/**
	 * Update port.
	 */
	private void updatePort() {
		boolean isSelected = chkboxClix.isSelected();

		if (!isSelected) {
			txtFieldPort.setText("");
		}

	}

	/**
	 * Update lbl if input not ok.
	 * 
	 * @return true, if successful
	 */
	private boolean updateLblIfInputNotOK() {
		if (txtFieldHostName.getText().isEmpty()) {
			lblResponseToUser.setText("Host Name Is Mandatory Field");

			return false;
		} else if (txtFieldUserName.getText().isEmpty()) {
			lblResponseToUser.setText("User Name Is Mandatory Field");

			return false;
		} else if (txtFieldPassword.getText().isEmpty()) {
			lblResponseToUser.setText("Password Is Mandatory Field");

			return false;
		} else if (txtFieldInstance.getText().isEmpty()) {
			lblResponseToUser.setText("Instance Is Mandatory Field");

			return false;
		} else if (!chkboxMdisr.isSelected() && !chkboxMdsr.isSelected()
				&& !chkboxMdssr.isSelected() && !chkboxClix.isSelected()) {
			lblResponseToUser.setText("Choose at least 1 Monitor Process");

			return false;
		} else if (!cmbStartSelection.getSelectionModel().isSelected(0)
				&& !cmbStartSelection.getSelectionModel().isSelected(1)) {
			lblResponseToUser.setText("Start Option Is Mandatory");

			return false;
		} else if (txtConfigurationID.getText().isEmpty()) {
			lblResponseToUser.setText("Name Configuration");

			return false;
		}
		// In Case Time Frame Was Selected
		else if (cmbStartSelection.getSelectionModel().isSelected(1)) {
			if (!checkIfInputTimeFrameAcceptableAsWhole(txtFieldTimeFrameFrom
					.getText())) {
				lblResponseToUser
						.setText("Time Frame From Should Be : dd-mm-yyyy HH:mm:ss");

				return false;
			} else if (!checkIfInputTimeFrameAcceptableAsWhole(txtFieldTimeFrameTo
					.getText())) {
				lblResponseToUser
						.setText("Time Frame To Should Be : dd-mm-yyyy HH:mm:ss");

				return false;
			}
		}

		// Check for connection OK with current settings
		String str = null;
		if ((str = checkIfCanBeConnectedWithCurrentSettings()) != null) {
			lblResponseToUser.setText(str);

			return false;
		}

		lblResponseToUser.setText("");

		return true;

	}

	/**
	 * Check if can be connected with current settings.
	 * 
	 * @return the string
	 */
	private String checkIfCanBeConnectedWithCurrentSettings() {

		GuiParameterCheck guiParamCheck = new GuiParameterCheck(
				txtFieldInstance.getText(), txtFieldHostName.getText(),
				txtFieldUserName.getText(), txtFieldPassword.getText());

		if (guiParamCheck.mainGuiCheck(txtFieldTimeFrameFrom.getText(),
				txtFieldTimeFrameTo.getText())) {
			return null;
		}

		return "Can't Connect With Current Settings";
	}

	/**
	 * Gets the current settings on view.
	 * 
	 * @return the current settings on view
	 */
	private Map<String, Object> getCurrentSettingsOnView() {
		Map<String, Object> map = new HashMap<String, Object>();

		if (!txtFieldConfigurationID.getText().isEmpty()) {
			map.put(CViewConstants.CONFIGURATION_ID,
					txtFieldConfigurationID.getText());
		} else if (cmbID.getSelectionModel().getSelectedIndex() >= 0) {
			map.put(CViewConstants.CONFIGURATION_ID, cmbID.getSelectionModel()
					.getSelectedItem());
		}

		if (!txtFieldHostName.getText().isEmpty()) {
			map.put(CViewConstants.HOSTNAME, txtFieldHostName.getText());
		}

		if (!txtFieldUserName.getText().isEmpty()) {
			map.put(CViewConstants.USERNAME, txtFieldUserName.getText());
		}

		if (!txtFieldPassword.getText().isEmpty()) {
			map.put(CViewConstants.PASSWORD, txtFieldPassword.getText());
		}

		if (!txtFieldInstance.getText().isEmpty()) {
			map.put(CViewConstants.INSTANCE, txtFieldInstance.getText());
		}

		if (!txtFieldInstanceTime.getText().isEmpty()) {
			map.put(CViewConstants.INTERVAL, txtFieldInstanceTime.getText());
		}

		map.put(CViewConstants.MDSR, chkboxMdsr.isSelected());
		map.put(CViewConstants.MDISR, chkboxMdisr.isSelected());
		map.put(CViewConstants.MDSSR, chkboxMdssr.isSelected());
		map.put(CViewConstants.CLIX, chkboxClix.isSelected());

		if (!txtFieldMemoryPop.getText().isEmpty()) {
			map.put(CViewConstants.MEMORY_POP, txtFieldMemoryPop.getText());
		}

		if (!txtFieldMemoryPop.getText().isEmpty()) {
			map.put(CViewConstants.MEMORY_POP, txtFieldMemoryPop.getText());
		}

		if (!txtFieldPort.getText().isEmpty()) {
			map.put(CViewConstants.PORT, txtFieldPort.getText());
		}

		int selectedIndex = cmbStartSelection.getSelectionModel()
				.getSelectedIndex();
		if (selectedIndex != -1) {
			if (selectedIndex == 0) {
				map.put(CViewConstants.START, CViewConstants.START_IMMEDIATELY);
			} else {
				map.put(CViewConstants.START, CViewConstants.START_FRAME_TIME);

				if (!txtFieldTimeFrameFrom.getText().isEmpty()) {
					map.put(CViewConstants.START_FROM_TIME,
							txtFieldTimeFrameFrom.getText());
				}

				if (!txtFieldTimeFrameTo.getText().isEmpty()) {
					map.put(CViewConstants.START_TO_TIME,
							txtFieldTimeFrameTo.getText());
				}
			}
		}

		return map;
	}

	/**
	 * Check if input time frame acceptable as whole.
	 * 
	 * @param text
	 *            the text
	 * @return true, if successful
	 */
	private boolean checkIfInputTimeFrameAcceptableAsWhole(String text) {
		if (text.isEmpty() || text.length() != 19)
			return false;

		if ((text.charAt(13) != ':') && (text.charAt(16) != ':')
				&& (text.charAt(10) != ' ') && (text.charAt(2) != '-')
				&& (text.charAt(5) != '-')) {
			return false;
		}

		if (!isDigit(text.charAt(0)) || !isDigit(text.charAt(1))
				|| !isDigit(text.charAt(3)) || !isDigit(text.charAt(4))
				|| !isDigit(text.charAt(6)) || !isDigit(text.charAt(7))
				|| !isDigit(text.charAt(8)) || !isDigit(text.charAt(9))
				|| !isDigit(text.charAt(11)) || !isDigit(text.charAt(12))
				|| !isDigit(text.charAt(14)) || !isDigit(text.charAt(15))
				|| !isDigit(text.charAt(17)) || !isDigit(text.charAt(18))) {
			return false;
		}

		return true;

	}
	

	/**
	 * Check if input time frame acceptable.
	 * 
	 * @param text
	 *            the text
	 * @param index
	 *            the index
	 * @return true, if successful
	 */
	private boolean checkIfInputTimeFrameAcceptable(String text) 
	{
		char c = ' ';
		boolean isOK = true;
		
		for (int i = 0; i < text.length() && isOK ; ++i)
		{
			c = text.charAt(i);
			
			switch (i)
			{
			case 0: // day 01 - 31
			{
				isOK = (c >= '0' && c <= '3');
				break;
			}
			case 1: {
				isOK = (isDigit(c) && ((text.charAt(0) == '3' && (c == '0' || c == '1')) || ((text
						.charAt(0) != '3' && c >= '0' && c <= '9')))); // day 01 -
																		// 31
				break;
			}
			case 2: {
				isOK = (c == '-');
				break;
			}
			case 3: // month 01-12
			{
				isOK = (c == '0' || c == '1');
				break;
			}
			case 4: {
				isOK = ((text.charAt(3) == '0' && c >= '0' && c <= '9') || (text
						.charAt(3) == '1' && c >= '0' && c <= '2'));
				break;
			}
			case 5: {
				isOK = (c == '-');
				break;
			}
			case 6: // years 2014 >
			{
				isOK = (c == '2');
				break;
			}
			case 7: {
				isOK = (c == '0');
				break;
			}
			case 8: {
				isOK = (c == '1' || c == '2');
				break;
			}
			case 9: {
				isOK = ((text.charAt(8) == '1' && c >= '4' && c <= '9') || (text
						.charAt(8) == '2' && c >= '0' && c <= '9'));
				break;
			}
			case 10: {
				isOK = (c == ' ');
				break;
			}
			case 11: // Hours 00 - 23
			{
				isOK = (c >= '0' && c <= '2');
				break;
			}
			case 12: {
				isOK = ((text.charAt(11) == '2' && c >= '0' && c <= '3') || (text
						.charAt(11) < '2' && c >= '0' && c <= '9'));
				break;
			}
			case 13: {
				isOK = (c == ':');
				break;
			}
			case 14: // Min 00 - 59
			{
				isOK = (c >= '0' && c <= '5');
				break;
			}
			case 15: {
				isOK = (c >= '0' && c <= '9');
				break;
			}
			case 16: {
				isOK = (c == ':');
				break;
			}
			case 17: // Sec 00 - 59
			{
				isOK = (c >= '0' && c <= '5');
				break;
			}
			case 18: 
			{
				isOK = (c >= '0' && c <= '9');
				break;
			}
			default:
			{
				isOK = false;
			}
	
			}
		}

		return isOK;

	}

}
