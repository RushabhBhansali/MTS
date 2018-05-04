package edu.gatech;

import edu.gatech.verifier.DoubleVerifier;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class MtsUi extends javax.swing.JFrame {

    private SimDriver simDriver;

    private static final String EMPTY = "";

    public MtsUi() {

        simDriver = SimDriver.getSimDriver();

        initComponents();

        panelAddBus.setVisible(false);
        panelAddBusStop.setVisible(false);
        panelAddBusRoute.setVisible(false);
        panelAddTrain.setVisible(false);
        panelAddTrainStop.setVisible(false);
        panelAddTrainRoute.setVisible(false);
        panelAddBusRider.setVisible(false);
        panelAddTrainRider.setVisible(false);
        panelExtendBusRoute.setVisible(false);
        panelExtendTrainRoute.setVisible(false);
        panelRandomBusRiders.setVisible(false);
        panelRandomTrainRiders.setVisible(false);
        panelAddBusTravelTimeFactor.setVisible(false);
        movingBusLabel.setVisible(false);
    }

    private void initComponents() {

        rb = ResourceBundle.getBundle("resources/mts");

        menuStepOnce = new javax.swing.JMenuItem();
        menuStepMulti = new javax.swing.JMenuItem();

        consoleLogs = new javax.swing.JScrollPane();
        consoleLogsTextArea = new javax.swing.JTextArea();
        consoleLogs.setSize(500,500);
        consoleLogsTextArea.setColumns(50);
        consoleLogsTextArea.setRows(25);
        consoleLogsTextArea.setEditable(false);
        consoleLogs.setViewportView(consoleLogsTextArea);

        consoleLogsDialog = new JDialog(this, false);
        consoleLogsDialog.setLayout(new FlowLayout());
        consoleLogsDialog.setSize(630,500);
        consoleLogsDialog.add(consoleLogs);
        if( getResourceText("resizeWindow").equalsIgnoreCase("false") ) {
            consoleLogsDialog.setResizable(false);
        }
        psOut = new PrintStream( new CustomOutputStream( consoleLogsTextArea ) );

        movingBusLabel = new JLabel();
        teamName = new JLabel();

        menuSimFactors = new javax.swing.JMenuItem();
        panelAddBusTravelTimeFactor = new javax.swing.JPanel();
        btnCloseBusTtFactorPanel = new javax.swing.JButton();
        busTtFactorRouteIdComboBox = new javax.swing.JComboBox<Integer>();
        busTtFactorStopIdComboBox = new javax.swing.JComboBox<Integer>();
        lblBusTtFactorRouteId = new javax.swing.JLabel();
        lblBusTtFactorStopId = new javax.swing.JLabel();
        lblPanelAddBusTravelTimeFactorHeading = new javax.swing.JLabel();
        btnAddBusTtFactor = new javax.swing.JButton();
        lblBusFactor = new javax.swing.JLabel();
        busTtFactor = new javax.swing.JTextField();
        menuBusTtFactor = new javax.swing.JMenuItem();

        menuEvent = new javax.swing.JMenu();
        menuMoveBusEvent = new javax.swing.JMenuItem();
        menuMoveTrainEvent = new javax.swing.JMenuItem();

        menuStartSimulation = new javax.swing.JMenu();

        panelRandomBusRiders = new javax.swing.JPanel();
        lblPanelRandomBusRidersHeading = new javax.swing.JLabel();
        lblMaxBusRiders = new javax.swing.JLabel();
        lblMinBusRiders = new javax.swing.JLabel();
        btnAddRandomBusRiders = new javax.swing.JButton();
        btnCloseRandomBusRiders = new javax.swing.JButton();
        minBusRiders = new javax.swing.JTextField();
        maxBusRiders = new javax.swing.JTextField();
        menuRandomBusRiders = new javax.swing.JMenuItem();

        panelRandomTrainRiders = new javax.swing.JPanel();
        lblPanelRandomTrainRidersHeading = new javax.swing.JLabel();
        lblMaxTrainRiders = new javax.swing.JLabel();
        lblMinTrainRiders = new javax.swing.JLabel();
        btnAddRandomTrainRiders = new javax.swing.JButton();
        btnCloseRandomTrainRiders = new javax.swing.JButton();
        minTrainRiders = new javax.swing.JTextField();
        maxTrainRiders = new javax.swing.JTextField();
        menuRandomTrainRiders = new javax.swing.JMenuItem();

        menuExtendBusRoute = new javax.swing.JMenuItem();
        menuExtendTrainRoute = new javax.swing.JMenuItem();
        panelExtendBusRoute = new javax.swing.JPanel();
        panelExtendTrainRoute = new javax.swing.JPanel();
        lblPanelExtendBusRouteHeading = new javax.swing.JLabel();
        lblPanelExtendTrainRouteHeading = new javax.swing.JLabel();
        lblBusAvgTravelTime = new javax.swing.JLabel();
        lblTrainAvgTravelTime = new javax.swing.JLabel();
        lblComboExtendBusRouteId = new javax.swing.JLabel();
        lblComboExtendTrainRouteId = new javax.swing.JLabel();
        lblComboExtendBusRouteBusStopId = new javax.swing.JLabel();
        lblComboExtendTrainRouteTrainStopId = new javax.swing.JLabel();
        btnExtendBusRoute = new javax.swing.JButton();
        btnCloseExtendBusRoute = new javax.swing.JButton();
        btnExtendTrainRoute = new javax.swing.JButton();
        btnCloseExtendTrainRoute = new javax.swing.JButton();
        extendBusRouteBusStopComboBox = new javax.swing.JComboBox<Integer>();
        extendTrainRouteTrainStopComboBox = new javax.swing.JComboBox<Integer>();
        extendBusRouteBusRouteIdComboBox = new javax.swing.JComboBox<Integer>();
        extendTrainRouteTrainRouteIdComboBox = new javax.swing.JComboBox<Integer>();
        avgBusTravelTime = new javax.swing.JTextField();
        avgTrainTravelTime = new javax.swing.JTextField();
        ///////// Add Bus Riders ////////////////////////
        lblPanelAddBusRiderHeading = new javax.swing.JLabel();
        panelAddBusRider = new javax.swing.JPanel();
        lblComboSrcBusStopId = new javax.swing.JLabel();
        srcBusStopIdComboBox = new javax.swing.JComboBox<Integer>();
        lblComboDestBusStopId = new javax.swing.JLabel();
        destBusStopIdComboBox = new javax.swing.JComboBox<Integer>();
        lblComboBusRouteId = new javax.swing.JLabel();
        busRouteIdComboBox = new javax.swing.JComboBox<Integer>();
        lblNoOfBusRiders = new javax.swing.JLabel();
        noOfBusRiders = new javax.swing.JTextField();
        btnAddBusRiders = new javax.swing.JButton();
        btnCloseAddBusRiders = new javax.swing.JButton();

        menuAddBusRiders = new javax.swing.JMenuItem();
        ////////////////////////////////////////////////

        lblPanelAddTrainRiderHeading = new javax.swing.JLabel();
        panelAddTrainRider = new javax.swing.JPanel();
        lblComboSrcTrainStopId = new javax.swing.JLabel();
        srcTrainStopIdComboBox = new javax.swing.JComboBox<Integer>();
        lblComboDestTrainStopId = new javax.swing.JLabel();
        destTrainStopIdComboBox = new javax.swing.JComboBox<Integer>();
        lblComboTrainRouteId = new javax.swing.JLabel();
        trainRouteIdComboBox = new javax.swing.JComboBox<Integer>();
        lblNoOfTrainRiders = new javax.swing.JLabel();
        noOfTrainRiders = new javax.swing.JTextField();
        btnAddTrainRiders = new javax.swing.JButton();
        btnCloseAddTrainRiders = new javax.swing.JButton();

        menuAddTrainRiders = new javax.swing.JMenuItem();
        ////////////////////////////////////////////////
        doubleVerifier = new DoubleVerifier();
        fileChooser = new javax.swing.JFileChooser();
        panelAddBus = new javax.swing.JPanel();
        lblBusId = new javax.swing.JLabel();
        busId = new javax.swing.JTextField();
        lblBusRoute = new javax.swing.JLabel();
        busRouteId = new javax.swing.JComboBox<Integer>();
        lblLocation = new javax.swing.JLabel();
        busLocation = new javax.swing.JTextField();
        lblTotalCapacity = new javax.swing.JLabel();
        busCapacity = new javax.swing.JTextField();
        lblBusSpeed = new javax.swing.JLabel();
        busSpeed = new javax.swing.JTextField();
        addBusDetailsPanelHeading = new javax.swing.JLabel();
        btnAddBus = new javax.swing.JButton();
        btnCloseAddBusPanel = new javax.swing.JButton();
        panelAddTrain = new javax.swing.JPanel();
        lblTrainId = new javax.swing.JLabel();
        uniqueTrainId = new javax.swing.JTextField();
        lblTrainRoute = new javax.swing.JLabel();
        trainRouteId = new javax.swing.JComboBox<Integer>();
        lblLocation1 = new javax.swing.JLabel();
        trainInputLocation = new javax.swing.JTextField();
        lblTotalCapacity1 = new javax.swing.JLabel();
        trainCapacity = new javax.swing.JTextField();
        lblTrainSpeed = new javax.swing.JLabel();
        trainSpeed = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnAddTrain = new javax.swing.JButton();
        btnCloseAddTrainPanel = new javax.swing.JButton();
        panelAddTrainStop = new javax.swing.JPanel();
        lblTrainStopId = new javax.swing.JLabel();
        uniqueTrainStopId = new javax.swing.JTextField();
        lblTrainStopName = new javax.swing.JLabel();
        trainStopName = new javax.swing.JTextField();
        lblTrainStopLatitude = new javax.swing.JLabel();
        trainStopLatitude = new javax.swing.JTextField();
        lblTrainStopLongitude = new javax.swing.JLabel();
        trainStopLongitude = new javax.swing.JTextField();
        addTrainStopHeading = new javax.swing.JLabel();
        btnAddTrainStop = new javax.swing.JButton();
        btnCloseAddTrainStopPanel = new javax.swing.JButton();
        panelAddBusRoute = new javax.swing.JPanel();
        lblBusRouteId = new javax.swing.JLabel();
        uniqueBusRouteId = new javax.swing.JTextField();
        lblBusRouteNumber = new javax.swing.JLabel();
        busRouteNumber = new javax.swing.JTextField();
        lblRouteName = new javax.swing.JLabel();
        busRouteName = new javax.swing.JTextField();
        addBusRouteHeading = new javax.swing.JLabel();
        btnAddBusRoute = new javax.swing.JButton();
        btnCloseAddBusRoutePanel = new javax.swing.JButton();
        panelAddBusStop = new javax.swing.JPanel();
        lblBusStopId = new javax.swing.JLabel();
        uniqueBusStopId = new javax.swing.JTextField();
        lblBusStopName = new javax.swing.JLabel();
        nameOfBusStop = new javax.swing.JTextField();
        lblBusStopLatitude = new javax.swing.JLabel();
        busStopLatitude = new javax.swing.JTextField();
        lblBusStopLongitude = new javax.swing.JLabel();
        busStopLongitude = new javax.swing.JTextField();
        addBusStopHeading = new javax.swing.JLabel();
        btnAddBusStop = new javax.swing.JButton();
        btnCloseAddBusStopPanel = new javax.swing.JButton();
        panelAddTrainRoute = new javax.swing.JPanel();
        lblTrainRouteId = new javax.swing.JLabel();
        uniqueTrainRouteId = new javax.swing.JTextField();
        lblTrainRouteNumber = new javax.swing.JLabel();
        trainRouteNumber = new javax.swing.JTextField();
        lblTrainRouteName = new javax.swing.JLabel();
        trainRouteName = new javax.swing.JTextField();
        addTrainRouteHeading = new javax.swing.JLabel();
        btnAddTrainRoute = new javax.swing.JButton();
        btnCloseAddTrainRoutePanel = new javax.swing.JButton();
        mtsMenuBar = new javax.swing.JMenuBar();
        menuBusSystem = new javax.swing.JMenu();
        menuAddBus = new javax.swing.JMenuItem();
        menuAddBusStop = new javax.swing.JMenuItem();
        menuAddBusRoute = new javax.swing.JMenuItem();
        menuTrainSystem = new javax.swing.JMenu();
        menuAddTrain = new javax.swing.JMenuItem();
        menuAddTrainStop = new javax.swing.JMenuItem();
        menuAddTrainRoute = new javax.swing.JMenuItem();
        menuSimConditions = new javax.swing.JMenu();
        menuReports = new javax.swing.JMenu();
        menuViewBusModel = new javax.swing.JMenuItem();
        menuViewTrainModel = new javax.swing.JMenuItem();
        menuViewBuses = new javax.swing.JMenuItem();
        menuViewTrains = new javax.swing.JMenuItem();
        menuViewBusRoutes = new javax.swing.JMenuItem();
        menuViewTrainRoutes = new javax.swing.JMenuItem();
        menuViewBusStops = new javax.swing.JMenuItem();
        menuViewTrainStops = new javax.swing.JMenuItem();

        menuUploadCommands = new javax.swing.JMenu();
        menuFileCommands = new javax.swing.JMenuItem();
        menuUploadDataFromDb =  new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(rb.getString("mtsTitle"));
        if( getResourceText("resizeWindow").toLowerCase().equalsIgnoreCase("false") ) {
            setResizable(false);
        }
        setPreferredSize(new java.awt.Dimension(Integer.parseInt(getResourceText("frameLength")),
                Integer.parseInt(getResourceText("frameWidth"))));
        getContentPane().setLayout(null);

        movingBusLabel.setBounds(200, 0, 800, 500);
        teamName.setBounds(50, 0, 600, 1150);
        teamName.setText(getResourceText("teamNameLabel"));
        teamName.setFont( teamName.getFont().deriveFont(20.0f));
        teamName.setHorizontalAlignment(SwingConstants.CENTER);
        teamName.setVerticalAlignment(SwingConstants.CENTER);

        movingBusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(getResourceText("busImagePath"))));

        getContentPane().add(movingBusLabel);
        getContentPane().add(teamName);

        lblBusId.setText(getResourceText("busIdLabel"));
        busId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblBusRoute.setText(getResourceText("busRouteLabel"));

        lblLocation.setText(getResourceText("busLocationLabel"));
        busLocation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblTotalCapacity.setText(getResourceText("busTotalCapacityLabel"));
        busCapacity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });


        lblBusSpeed.setText(getResourceText("busSpeedLabel"));
        busSpeed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });


        addBusDetailsPanelHeading.setText(getResourceText("panelAddBusHeading"));

        btnAddBus.setText(getResourceText("addBtnText"));
        btnAddBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBusActionPerformed(evt);
            }
        });

        btnCloseAddBusPanel.setText(getResourceText("closeBtnText"));
        btnCloseAddBusPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddBusPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddBusLayout = new javax.swing.GroupLayout(panelAddBus);
        panelAddBus.setLayout(panelAddBusLayout);
        panelAddBusLayout.setHorizontalGroup(
                panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(addBusDetailsPanelHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(panelAddBusLayout.createSequentialGroup()
                                                        .addGap(1, 1, 1)
                                                        .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lblBusRoute)
                                                                .addComponent(lblBusId))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(busRouteId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(busId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(panelAddBusLayout.createSequentialGroup()
                                                        .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblBusSpeed)
                                                                        .addComponent(lblTotalCapacity))
                                                                .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(btnAddBus, javax.swing.GroupLayout.Alignment.TRAILING))
                                                        .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(panelAddBusLayout.createSequentialGroup()
                                                                        .addGap(23, 23, 23)
                                                                        .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addGroup(panelAddBusLayout.createSequentialGroup()
                                                                                        .addGap(9, 9, 9)
                                                                                        .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                        .addComponent(busCapacity)
                                                                                                        .addComponent(busSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                ))
                                                                                .addComponent(busLocation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddBusLayout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(btnCloseAddBusPanel)
                                                                        .addGap(30, 30, 30))))))
                                .addContainerGap(67, Short.MAX_VALUE))
        );
        panelAddBusLayout.setVerticalGroup(
                panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addBusDetailsPanelHeading)
                                .addGap(19, 19, 19)
                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblBusId)
                                        .addComponent(busId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(busRouteId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblBusRoute))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(busLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblLocation))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTotalCapacity)
                                        .addComponent(busCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(busSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblBusSpeed))
                                .addGap(18, 18, 18)
                                .addGroup(panelAddBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddBus)
                                        .addComponent(btnCloseAddBusPanel))
                                .addContainerGap(33, Short.MAX_VALUE))
        );

        lblTrainId.setText(getResourceText("trainIdLabel"));
        uniqueTrainId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblTrainRoute.setText(getResourceText("trainRouteLabel"));

        lblLocation1.setText(getResourceText("trainLocationLabel"));
        trainInputLocation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblTotalCapacity1.setText(getResourceText("trainTotalCapacityLabel"));
        trainCapacity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblTrainSpeed.setText(getResourceText("trainSpeedLabel"));
        trainSpeed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        jLabel4.setText(getResourceText("panelAddTrainHeading"));

        btnAddTrain.setText(getResourceText("addBtnText"));
        btnAddTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTrainActionPerformed(evt);
            }
        });

        btnCloseAddTrainPanel.setText(getResourceText("closeBtnText"));
        btnCloseAddTrainPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddTrainPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddTrainLayout = new javax.swing.GroupLayout(panelAddTrain);
        panelAddTrain.setLayout(panelAddTrainLayout);
        panelAddTrainLayout.setHorizontalGroup(
                panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddTrainLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(panelAddTrainLayout.createSequentialGroup()
                                                        .addGap(1, 1, 1)
                                                        .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lblTrainRoute)
                                                                .addComponent(lblTrainId))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(trainRouteId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(uniqueTrainId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(panelAddTrainLayout.createSequentialGroup()
                                                        .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblTrainSpeed)
                                                                        .addComponent(lblTotalCapacity1))
                                                                .addComponent(lblLocation1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(btnAddTrain, javax.swing.GroupLayout.Alignment.TRAILING))
                                                        .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(panelAddTrainLayout.createSequentialGroup()
                                                                        .addGap(23, 23, 23)
                                                                        .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addGroup(panelAddTrainLayout.createSequentialGroup()
                                                                                        .addGap(9, 9, 9)
                                                                                        .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                        .addComponent(trainCapacity)
                                                                                                        .addComponent(trainSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)) ))
                                                                                .addComponent(trainInputLocation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddTrainLayout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(btnCloseAddTrainPanel)
                                                                        .addGap(30, 30, 30)))))
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(72, Short.MAX_VALUE))
        );
        panelAddTrainLayout.setVerticalGroup(
                panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddTrainLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)
                                .addGap(19, 19, 19)
                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTrainId)
                                        .addComponent(uniqueTrainId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(trainRouteId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTrainRoute))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(trainInputLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblLocation1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTotalCapacity1)
                                        .addComponent(trainCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(trainSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTrainSpeed))
                                .addGap(18, 18, 18)
                                .addGroup(panelAddTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddTrain)
                                        .addComponent(btnCloseAddTrainPanel))
                                .addContainerGap(33, Short.MAX_VALUE))
        );

        lblTrainStopId.setText(getResourceText("trainStopIdLabel"));

        lblTrainStopName.setText(getResourceText("trainStopNameLabel"));

        lblTrainStopLatitude.setText(getResourceText("trainLatitudeLabel"));

        lblTrainStopLongitude.setText(getResourceText("trainLongitudeLabel"));

        addTrainStopHeading.setText(getResourceText("panelAddTrainStopHeading"));

        btnAddTrainStop.setText(getResourceText("addBtnText"));
        btnAddTrainStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTrainStopActionPerformed(evt);
            }
        });

        btnCloseAddTrainStopPanel.setText(getResourceText("closeBtnText"));
        btnCloseAddTrainStopPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddTrainStopPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddTrainStopLayout = new javax.swing.GroupLayout(panelAddTrainStop);
        panelAddTrainStop.setLayout(panelAddTrainStopLayout);
        panelAddTrainStopLayout.setHorizontalGroup(
                panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddTrainStopLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(panelAddTrainStopLayout.createSequentialGroup()
                                                        .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lblTrainStopLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblTrainStopLongitude))
                                                        .addGap(38, 38, 38)
                                                        .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(panelAddTrainStopLayout.createSequentialGroup()
                                                                        .addGap(9, 9, 9)
                                                                        .addComponent(trainStopLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(trainStopLatitude, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(panelAddTrainStopLayout.createSequentialGroup()
                                                        .addGap(1, 1, 1)
                                                        .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(addTrainStopHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(panelAddTrainStopLayout.createSequentialGroup()
                                                                        .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(lblTrainStopName)
                                                                                .addComponent(lblTrainStopId))
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(trainStopName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(uniqueTrainStopId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                        .addGroup(panelAddTrainStopLayout.createSequentialGroup()
                                                .addGap(46, 46, 46)
                                                .addComponent(btnAddTrainStop)
                                                .addGap(26, 26, 26)
                                                .addComponent(btnCloseAddTrainStopPanel)))
                                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelAddTrainStopLayout.setVerticalGroup(
                panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddTrainStopLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addTrainStopHeading)
                                .addGap(19, 19, 19)
                                .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTrainStopId)
                                        .addComponent(uniqueTrainStopId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(trainStopName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTrainStopName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(trainStopLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTrainStopLatitude))
                                .addGap(11, 11, 11)
                                .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(trainStopLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTrainStopLongitude))
                                .addGap(18, 18, 18)
                                .addGroup(panelAddTrainStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddTrainStop)
                                        .addComponent(btnCloseAddTrainStopPanel))
                                .addContainerGap(19, Short.MAX_VALUE))
        );

        lblBusRouteId.setText(getResourceText("busRouteIdLabel"));
        uniqueBusRouteId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblBusRouteNumber.setText(getResourceText("busRouteNumberLabel"));
        busRouteNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblRouteName.setText(getResourceText("busRouteNameLabel"));

        addBusRouteHeading.setText(getResourceText("panelAddBusRouteHeading"));

        btnAddBusRoute.setText(getResourceText("addBtnText"));
        btnAddBusRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBusRouteActionPerformed(evt);
            }
        });

        btnCloseAddBusRoutePanel.setText(getResourceText("closeBtnText"));
        btnCloseAddBusRoutePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddBusRoutePanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddBusRouteLayout = new javax.swing.GroupLayout(panelAddBusRoute);
        panelAddBusRoute.setLayout(panelAddBusRouteLayout);
        panelAddBusRouteLayout.setHorizontalGroup(
                panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusRouteLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelAddBusRouteLayout.createSequentialGroup()
                                                .addComponent(addBusRouteHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(panelAddBusRouteLayout.createSequentialGroup()
                                                .addGroup(panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(lblRouteName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblBusRouteNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblBusRouteId, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(busRouteNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(uniqueBusRouteId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(busRouteName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(38, 38, 38))
                        .addGroup(panelAddBusRouteLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(btnAddBusRoute)
                                .addGap(26, 26, 26)
                                .addComponent(btnCloseAddBusRoutePanel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAddBusRouteLayout.setVerticalGroup(
                panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusRouteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addBusRouteHeading)
                                .addGap(19, 19, 19)
                                .addGroup(panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblBusRouteId)
                                        .addComponent(uniqueBusRouteId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(busRouteNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblBusRouteNumber))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(busRouteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblRouteName))
                                .addGap(18, 18, 18)
                                .addGroup(panelAddBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddBusRoute)
                                        .addComponent(btnCloseAddBusRoutePanel))
                                .addContainerGap(42, Short.MAX_VALUE))
        );

        lblBusStopId.setText(getResourceText("busStopIdLabel"));
        uniqueBusStopId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });
        lblBusStopName.setText(getResourceText("busStopNameLabel"));
        lblBusStopLatitude.setText(getResourceText("busStopLatitudeLabel"));
        lblBusStopLongitude.setText(getResourceText("busStopLongitudeLabel"));

        addBusStopHeading.setText(getResourceText("panelBusStopHeading"));

        btnAddBusStop.setText(getResourceText("addBtnText"));
        btnAddBusStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBusStopActionPerformed(evt);
            }
        });

        btnCloseAddBusStopPanel.setText(getResourceText("closeBtnText"));
        btnCloseAddBusStopPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddBusStopPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddBusStopLayout = new javax.swing.GroupLayout(panelAddBusStop);
        panelAddBusStop.setLayout(panelAddBusStopLayout);
        panelAddBusStopLayout.setHorizontalGroup(
                panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusStopLayout.createSequentialGroup()
                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelAddBusStopLayout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(panelAddBusStopLayout.createSequentialGroup()
                                                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblBusStopName)
                                                                        .addComponent(lblBusStopId))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(nameOfBusStop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(uniqueBusStopId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(panelAddBusStopLayout.createSequentialGroup()
                                                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(addBusStopHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(panelAddBusStopLayout.createSequentialGroup()
                                                                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(lblBusStopLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(lblBusStopLongitude))
                                                                                .addGap(29, 29, 29)
                                                                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(panelAddBusStopLayout.createSequentialGroup()
                                                                                                .addGap(47, 47, 47)
                                                                                                .addComponent(busStopLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddBusStopLayout.createSequentialGroup()
                                                                                                .addGap(38, 38, 38)
                                                                                                .addComponent(busStopLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(panelAddBusStopLayout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnAddBusStop)
                                                .addGap(35, 35, 35)
                                                .addComponent(btnCloseAddBusStopPanel)
                                                .addGap(24, 24, 24)))
                                .addContainerGap(58, Short.MAX_VALUE))
        );
        panelAddBusStopLayout.setVerticalGroup(
                panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusStopLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addBusStopHeading)
                                .addGap(19, 19, 19)
                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblBusStopId)
                                        .addComponent(uniqueBusStopId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameOfBusStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblBusStopName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(busStopLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblBusStopLatitude))
                                .addGap(11, 11, 11)
                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(busStopLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblBusStopLongitude))
                                .addGap(18, 18, 18)
                                .addGroup(panelAddBusStopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddBusStop)
                                        .addComponent(btnCloseAddBusStopPanel))
                                .addContainerGap(42, Short.MAX_VALUE))
        );

        lblTrainRouteId.setText(getResourceText("trainRouteIdLabel"));
        uniqueTrainRouteId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });
        lblTrainRouteNumber.setText(getResourceText("trainRouteNumberLabel"));
        lblTrainRouteName.setText(getResourceText("trainRouteNameLabel"));
        addTrainRouteHeading.setText(getResourceText("panelAddTrainRouteHeading"));

        btnAddTrainRoute.setText(getResourceText("addBtnText"));
        btnAddTrainRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTrainRouteActionPerformed(evt);
            }
        });

        btnCloseAddTrainRoutePanel.setText(getResourceText("closeBtnText"));
        btnCloseAddTrainRoutePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddTrainRoutePanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddTrainRouteLayout = new javax.swing.GroupLayout(panelAddTrainRoute);
        panelAddTrainRoute.setLayout(panelAddTrainRouteLayout);
        panelAddTrainRouteLayout.setHorizontalGroup(
                panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddTrainRouteLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelAddTrainRouteLayout.createSequentialGroup()
                                                .addComponent(addTrainRouteHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(panelAddTrainRouteLayout.createSequentialGroup()
                                                .addGroup(panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(lblTrainRouteName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblTrainRouteNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblTrainRouteId, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(trainRouteNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(uniqueTrainRouteId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(trainRouteName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(38, 38, 38))
                        .addGroup(panelAddTrainRouteLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(btnAddTrainRoute)
                                .addGap(26, 26, 26)
                                .addComponent(btnCloseAddTrainRoutePanel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAddTrainRouteLayout.setVerticalGroup(
                panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddTrainRouteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addTrainRouteHeading)
                                .addGap(19, 19, 19)
                                .addGroup(panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTrainRouteId)
                                        .addComponent(uniqueTrainRouteId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(trainRouteNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTrainRouteNumber))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(trainRouteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTrainRouteName))
                                .addGap(18, 18, 18)
                                .addGroup(panelAddTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddTrainRoute)
                                        .addComponent(btnCloseAddTrainRoutePanel))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        lblComboSrcBusStopId.setText(getResourceText("srcBusStopIdComboxBoxLabel"));

        lblPanelAddBusRiderHeading.setText(getResourceText("panelAddBusRiderHeading"));

        lblComboDestBusStopId.setText(getResourceText("destBusStopIdComboxBoxLabel"));

        lblNoOfBusRiders.setText(getResourceText("noOfBusRidersLabel"));

        lblComboBusRouteId.setText(getResourceText("busRouteComboBoxLabel"));

        btnAddBusRiders.setText(getResourceText("addBtnText"));
        btnAddBusRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBusRidersActionPerformed(evt);
            }
        });

        btnCloseAddBusRiders.setText(getResourceText("closeBtnText"));
        btnCloseAddBusRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddBusRidersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddBusRiderLayout = new javax.swing.GroupLayout(panelAddBusRider);
        panelAddBusRider.setLayout(panelAddBusRiderLayout);
        panelAddBusRiderLayout.setHorizontalGroup(
                panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusRiderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panelAddBusRiderLayout.createSequentialGroup()
                                                .addComponent(lblNoOfBusRiders)
                                                .addGap(31, 31, 31)
                                                .addComponent(noOfBusRiders))
                                        .addComponent(lblComboSrcBusStopId)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblPanelAddBusRiderHeading)
                                                .addGroup(panelAddBusRiderLayout.createSequentialGroup()
                                                        .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(btnAddBusRiders)
                                                                        .addComponent(lblComboDestBusStopId))
                                                                .addComponent(lblComboBusRouteId))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(busRouteIdComboBox)
                                                                .addComponent(destBusStopIdComboBox)
                                                                .addComponent(btnCloseAddBusRiders)
                                                                .addComponent(srcBusStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelAddBusRiderLayout.setVerticalGroup(
                panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusRiderLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(lblPanelAddBusRiderHeading)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(srcBusStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblComboSrcBusStopId))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblComboDestBusStopId)
                                        .addComponent(destBusStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblComboBusRouteId)
                                        .addComponent(busRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNoOfBusRiders)
                                        .addComponent(noOfBusRiders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelAddBusRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddBusRiders)
                                        .addComponent(btnCloseAddBusRiders))
                                .addContainerGap(13, Short.MAX_VALUE))
        );


        lblPanelExtendBusRouteHeading.setText(getResourceText("extendBusRoutePanelHeading"));

        lblBusAvgTravelTime.setText(getResourceText("extendBusRouteAvgTravelTime"));

        lblComboExtendBusRouteId.setText(getResourceText("extendBusRouteRouteId"));

        btnExtendBusRoute.setText(getResourceText("extendBusRouteExtendButton"));
        btnExtendBusRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExtendBusRouteActionPerformed(evt);
            }
        });

        btnCloseExtendBusRoute.setText(getResourceText("closeBtnText"));
        btnCloseExtendBusRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseExtendBusRouteActionPerformed(evt);
            }
        });

        lblComboExtendBusRouteBusStopId.setText(getResourceText("extendBusRouteBusStopId"));

        javax.swing.GroupLayout panelExtendBusRouteLayout = new javax.swing.GroupLayout(panelExtendBusRoute);
        panelExtendBusRoute.setLayout(panelExtendBusRouteLayout);
        panelExtendBusRouteLayout.setHorizontalGroup(
                panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelExtendBusRouteLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblPanelExtendBusRouteHeading)
                                        .addGroup(panelExtendBusRouteLayout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(btnExtendBusRoute)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnCloseExtendBusRoute))
                                        .addGroup(panelExtendBusRouteLayout.createSequentialGroup()
                                                .addComponent(lblBusAvgTravelTime)
                                                .addGap(18, 18, 18)
                                                .addComponent(avgBusTravelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelExtendBusRouteLayout.createSequentialGroup()
                                                .addGroup(panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblComboExtendBusRouteId)
                                                        .addComponent(lblComboExtendBusRouteBusStopId))
                                                .addGap(40, 40, 40)
                                                .addGroup(panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(extendBusRouteBusStopComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(extendBusRouteBusRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelExtendBusRouteLayout.setVerticalGroup(
                panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelExtendBusRouteLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(lblPanelExtendBusRouteHeading)
                                .addGap(24, 24, 24)
                                .addGroup(panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblComboExtendBusRouteId)
                                        .addComponent(extendBusRouteBusRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblComboExtendBusRouteBusStopId)
                                        .addComponent(extendBusRouteBusStopComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblBusAvgTravelTime)
                                        .addComponent(avgBusTravelTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelExtendBusRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnExtendBusRoute)
                                        .addComponent(btnCloseExtendBusRoute))
                                .addContainerGap(20, Short.MAX_VALUE))
        );


        lblComboSrcTrainStopId.setText(getResourceText("srcTrainStopIdComboxBoxLabel"));

        lblPanelAddTrainRiderHeading.setText(getResourceText("panelAddTrainRiderHeading"));

        lblComboDestTrainStopId.setText(getResourceText("destTrainStopIdComboxBoxLabel"));

        lblNoOfTrainRiders.setText(getResourceText("noOfTrainRidersLabel"));
        noOfTrainRiders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblComboTrainRouteId.setText(getResourceText("trainRouteComboBoxLabel"));

        btnAddTrainRiders.setText(getResourceText("addBtnText"));
        btnAddTrainRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTrainRidersActionPerformed(evt);
            }
        });

        btnCloseAddTrainRiders.setText(getResourceText("closeBtnText"));
        btnCloseAddTrainRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddTrainRidersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddTrainRiderLayout = new javax.swing.GroupLayout(panelAddTrainRider);
        panelAddTrainRider.setLayout(panelAddTrainRiderLayout);
        panelAddTrainRiderLayout.setHorizontalGroup(
                panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddTrainRiderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panelAddTrainRiderLayout.createSequentialGroup()
                                                .addComponent(lblNoOfTrainRiders)
                                                .addGap(31, 31, 31)
                                                .addComponent(noOfTrainRiders))
                                        .addComponent(lblComboSrcTrainStopId)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblPanelAddTrainRiderHeading)
                                                .addGroup(panelAddTrainRiderLayout.createSequentialGroup()
                                                        .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(btnAddTrainRiders)
                                                                        .addComponent(lblComboDestTrainStopId))
                                                                .addComponent(lblComboTrainRouteId))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(trainRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(destTrainStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(btnCloseAddTrainRiders)
                                                                .addComponent(srcTrainStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelAddTrainRiderLayout.setVerticalGroup(
                panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddTrainRiderLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(lblPanelAddTrainRiderHeading)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(srcTrainStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblComboSrcTrainStopId))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblComboDestTrainStopId)
                                        .addComponent(destTrainStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblComboTrainRouteId)
                                        .addComponent(trainRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNoOfTrainRiders)
                                        .addComponent(noOfTrainRiders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelAddTrainRiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddTrainRiders)
                                        .addComponent(btnCloseAddTrainRiders))
                                .addContainerGap(13, Short.MAX_VALUE))
        );


        lblPanelExtendTrainRouteHeading.setText(getResourceText("extendTrainRoutePanelHeading"));

        lblTrainAvgTravelTime.setText(getResourceText("extendTrainRouteAvgTravelTime"));

        lblComboExtendTrainRouteId.setText(getResourceText("extendTrainRouteRouteId"));

        btnExtendTrainRoute.setText(getResourceText("extendTrainRouteExtendButton"));
        btnExtendTrainRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExtendTrainRouteActionPerformed(evt);
            }
        });

        btnCloseExtendTrainRoute.setText(getResourceText("closeBtnText"));
        btnCloseExtendTrainRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseExtendTrainRouteActionPerformed(evt);
            }
        });

        lblComboExtendTrainRouteTrainStopId.setText(getResourceText("extendTrainRouteTrainStopId"));

        javax.swing.GroupLayout panelExtendTrainRouteLayout = new javax.swing.GroupLayout(panelExtendTrainRoute);
        panelExtendTrainRoute.setLayout(panelExtendTrainRouteLayout);
        panelExtendTrainRouteLayout.setHorizontalGroup(
                panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelExtendTrainRouteLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblPanelExtendTrainRouteHeading)
                                        .addGroup(panelExtendTrainRouteLayout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(btnExtendTrainRoute)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnCloseExtendTrainRoute))
                                        .addGroup(panelExtendTrainRouteLayout.createSequentialGroup()
                                                .addComponent(lblTrainAvgTravelTime)
                                                .addGap(18, 18, 18)
                                                .addComponent(avgTrainTravelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelExtendTrainRouteLayout.createSequentialGroup()
                                                .addGroup(panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblComboExtendTrainRouteId)
                                                        .addComponent(lblComboExtendTrainRouteTrainStopId))
                                                .addGap(40, 40, 40)
                                                .addGroup(panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(extendTrainRouteTrainStopComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(extendTrainRouteTrainRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelExtendTrainRouteLayout.setVerticalGroup(
                panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelExtendTrainRouteLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(lblPanelExtendTrainRouteHeading)
                                .addGap(24, 24, 24)
                                .addGroup(panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblComboExtendTrainRouteId)
                                        .addComponent(extendTrainRouteTrainRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblComboExtendTrainRouteTrainStopId)
                                        .addComponent(extendTrainRouteTrainStopComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTrainAvgTravelTime)
                                        .addComponent(avgTrainTravelTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelExtendTrainRouteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnExtendTrainRoute)
                                        .addComponent(btnCloseExtendTrainRoute))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        lblMinBusRiders.setText(getResourceText("minBusRidersLabel"));
        minBusRiders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblMaxBusRiders.setText(getResourceText("maxBusRidersLabel"));
        maxBusRiders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        lblPanelRandomBusRidersHeading.setText(getResourceText("panelRandomBusRidersHeading"));

        btnAddRandomBusRiders.setText(getResourceText("addBtnText"));
        btnAddRandomBusRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRandomBusRidersActionPerformed(evt);
            }
        });

        btnCloseRandomBusRiders.setText(getResourceText("closeBtnText"));
        btnCloseRandomBusRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseRandomBusRidersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRandomBusRidersLayout = new javax.swing.GroupLayout(panelRandomBusRiders);
        panelRandomBusRiders.setLayout(panelRandomBusRidersLayout);
        panelRandomBusRidersLayout.setHorizontalGroup(
                panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRandomBusRidersLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelRandomBusRidersLayout.createSequentialGroup()
                                                .addGroup(panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(panelRandomBusRidersLayout.createSequentialGroup()
                                                                .addGroup(panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblMinBusRiders)
                                                                        .addComponent(lblMaxBusRiders, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(maxBusRiders, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(minBusRiders, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(panelRandomBusRidersLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(btnAddRandomBusRiders)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnCloseRandomBusRiders)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(panelRandomBusRidersLayout.createSequentialGroup()
                                                .addComponent(lblPanelRandomBusRidersHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())))
        );
        panelRandomBusRidersLayout.setVerticalGroup(
                panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRandomBusRidersLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblPanelRandomBusRidersHeading)
                                .addGap(18, 18, 18)
                                .addGroup(panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMinBusRiders)
                                        .addComponent(minBusRiders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(maxBusRiders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMaxBusRiders))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelRandomBusRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddRandomBusRiders)
                                        .addComponent(btnCloseRandomBusRiders))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblMinTrainRiders.setText(getResourceText("minTrainRidersLabel"));
        minTrainRiders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });
        lblMaxTrainRiders.setText(getResourceText("maxTrainRidersLabel"));
        maxTrainRiders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });
        lblPanelRandomTrainRidersHeading.setText(getResourceText("panelRandomTrainRidersHeading"));

        btnAddRandomTrainRiders.setText(getResourceText("addBtnText"));
        btnAddRandomTrainRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRandomTrainRidersActionPerformed(evt);
            }
        });

        btnCloseRandomTrainRiders.setText(getResourceText("closeBtnText"));
        btnCloseRandomTrainRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseRandomTrainRidersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRandomTrainRidersLayout = new javax.swing.GroupLayout(panelRandomTrainRiders);
        panelRandomTrainRiders.setLayout(panelRandomTrainRidersLayout);
        panelRandomTrainRidersLayout.setHorizontalGroup(
                panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRandomTrainRidersLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelRandomTrainRidersLayout.createSequentialGroup()
                                                .addGroup(panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(panelRandomTrainRidersLayout.createSequentialGroup()
                                                                .addGroup(panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblMinTrainRiders)
                                                                        .addComponent(lblMaxTrainRiders, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(maxTrainRiders, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(minTrainRiders, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(panelRandomTrainRidersLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(btnAddRandomTrainRiders)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnCloseRandomTrainRiders)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(panelRandomTrainRidersLayout.createSequentialGroup()
                                                .addComponent(lblPanelRandomTrainRidersHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())))
        );

        panelRandomTrainRidersLayout.setVerticalGroup(
                panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRandomTrainRidersLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblPanelRandomTrainRidersHeading)
                                .addGap(18, 18, 18)
                                .addGroup(panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMinTrainRiders)
                                        .addComponent(minTrainRiders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(maxTrainRiders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMaxTrainRiders))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelRandomTrainRidersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddRandomTrainRiders)
                                        .addComponent(btnCloseRandomTrainRiders))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblBusTtFactorRouteId.setText(getResourceText("busTtFactorRouteIdLabel"));
        lblBusTtFactorStopId.setText(getResourceText("busTtFactorStopIdLabel"));
        lblPanelAddBusTravelTimeFactorHeading.setText(getResourceText("busTtFactorPanelHeading"));

        btnAddBusTtFactor.setText(getResourceText("busTtAdd"));
        btnAddBusTtFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBusTtFactorActionPerformed(evt);
            }
        });

        btnCloseBusTtFactorPanel.setText(getResourceText("busTtClose"));
        btnCloseBusTtFactorPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseBusTtFactorPanelActionPerformed(evt);
            }
        });

        lblBusFactor.setText(getResourceText("busTtFactorLabel"));

        javax.swing.GroupLayout panelAddBusTravelTimeFactorLayout = new javax.swing.GroupLayout(panelAddBusTravelTimeFactor);
        panelAddBusTravelTimeFactor.setLayout(panelAddBusTravelTimeFactorLayout);
        panelAddBusTravelTimeFactorLayout.setHorizontalGroup(
                panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusTravelTimeFactorLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblPanelAddBusTravelTimeFactorHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panelAddBusTravelTimeFactorLayout.createSequentialGroup()
                                                .addGroup(panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(btnAddBusTtFactor)
                                                                .addGroup(panelAddBusTravelTimeFactorLayout.createSequentialGroup()
                                                                        .addComponent(lblBusTtFactorRouteId)
                                                                        .addGap(17, 17, 17)))
                                                        .addComponent(lblBusFactor)
                                                        .addComponent(lblBusTtFactorStopId, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(busTtFactor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(busTtFactorRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(busTtFactorStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnCloseBusTtFactorPanel))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        panelAddBusTravelTimeFactorLayout.setVerticalGroup(
                panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAddBusTravelTimeFactorLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblPanelAddBusTravelTimeFactorHeading)
                                .addGap(18, 18, 18)
                                .addGroup(panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblBusTtFactorRouteId)
                                        .addComponent(busTtFactorRouteIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(busTtFactorStopIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblBusTtFactorStopId))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblBusFactor)
                                        .addComponent(busTtFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddBusTravelTimeFactorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddBusTtFactor)
                                        .addComponent(btnCloseBusTtFactorPanel))
                                .addGap(31, 31, 31))
        );

        menuBusSystem.setText(getResourceText("menuBusSystem"));
        menuBusSystem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBusSystemMouseClicked(evt);
            }
        });

        menuAddBus.setText(getResourceText("menuAddBus"));
        menuAddBus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuAddBusMouseClicked(evt);
            }
        });
        menuAddBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddBusActionPerformed(evt);
            }
        });

        menuAddBusStop.setText(getResourceText("menuAddBusStop"));
        menuAddBusStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddBusStopActionPerformed(evt);
            }
        });

        menuAddBusRoute.setText(getResourceText("menuAddBusRoute"));
        menuAddBusRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddBusRouteActionPerformed(evt);
            }
        });

        menuExtendBusRoute.setText(getResourceText("menuExtendBusRoute"));
        menuExtendBusRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExtendBusRouteActionPerformed(evt);
            }
        });

        menuRandomBusRiders.setText(getResourceText("menuGenerateRandomBusRiders"));
        menuRandomBusRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRandomBusRidersActionPerformed(evt);
            }
        });

        menuBusSystem.add(menuAddBusStop);
        menuBusSystem.add(menuAddBusRoute);
        menuBusSystem.add(menuExtendBusRoute);
        menuBusSystem.add(menuAddBus);
        menuBusSystem.add(menuRandomBusRiders);


        mtsMenuBar.add(menuBusSystem);

        menuTrainSystem.setText(getResourceText("menuTrainSystem"));
        menuTrainSystem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuTrainSystemMouseClicked(evt);
            }
        });

        menuAddTrain.setText(getResourceText("menuAddTrain"));
        menuAddTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddTrainActionPerformed(evt);
            }
        });

        menuAddTrainStop.setText(getResourceText("menuAddTrainStop"));
        menuAddTrainStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddTrainStopActionPerformed(evt);
            }
        });

        menuAddTrainRoute.setText(getResourceText("menuAddTrainRoute"));
        menuAddTrainRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddTrainRouteActionPerformed(evt);
            }
        });

        menuExtendTrainRoute.setText(getResourceText("menuExtendTrainRoute"));
        menuExtendTrainRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExtendTrainRouteActionPerformed(evt);
            }
        });

        menuRandomTrainRiders.setText(getResourceText("menuGenerateRandomTrainRiders"));
        menuRandomTrainRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRandomTrainRidersActionPerformed(evt);
            }
        });

        menuTrainSystem.add(menuAddTrainStop);
        menuTrainSystem.add(menuAddTrainRoute);
        menuTrainSystem.add(menuExtendTrainRoute);
        menuTrainSystem.add(menuAddTrain);
        menuTrainSystem.add(menuRandomTrainRiders);

        mtsMenuBar.add(menuTrainSystem);

        menuSimFactors.setText(getResourceText("menuSimFactors"));
        menuSimFactors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSimFactorsActionPerformed(evt);
            }
        });

        menuSimConditions.setText(getResourceText("menuSimConditions"));
        menuSimConditions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSimConditionsMouseClicked(evt);
            }
        });


        menuAddBusRiders.setText(getResourceText("menuAddBusRiders"));
        menuAddBusRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddBusRidersActionPerformed(evt);
            }
        });

        menuAddTrainRiders.setText(getResourceText("menuAddTrainRiders"));
        menuAddTrainRiders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddTrainRidersActionPerformed(evt);
            }
        });

        menuBusTtFactor.setText(getResourceText("menuBusTravelTimeFactor"));
        menuBusTtFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBusTtFactorActionPerformed(evt);
            }
        });

        menuSimConditions.add(menuSimFactors);
        menuSimConditions.add(menuAddBusRiders);
        menuSimConditions.add(menuAddTrainRiders);
        menuSimConditions.add(menuBusTtFactor);

        mtsMenuBar.add(menuSimConditions);

        menuEvent.setText(getResourceText("menuEvent"));

        menuMoveBusEvent.setText(getResourceText("menuMoveBus"));
        menuMoveBusEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMoveBusEventActionPerformed(evt);
            }
        });
        menuEvent.add(menuMoveBusEvent);

        menuMoveTrainEvent.setText(getResourceText("menuMoveTrain"));
        menuMoveTrainEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMoveTrainEventActionPerformed(evt);
            }
        });
        menuEvent.add(menuMoveTrainEvent);

        mtsMenuBar.add(menuEvent);

        menuUploadCommands.setText(getResourceText("menuUploadCommand"));

        menuFileCommands.setText(getResourceText("menuUploadFileCommands"));
        menuFileCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileCommandsActionPerformed(evt);
            }
        });

        menuUploadDataFromDb.setText(getResourceText("menuUploadDataFromDatabase"));
        menuUploadDataFromDb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUploadDataFromDbActionPerformed(evt);
            }
        });

        menuUploadCommands.add( menuFileCommands );
        menuUploadCommands.add( menuUploadDataFromDb );

        mtsMenuBar.add(menuUploadCommands);

        menuStartSimulation.setText(getResourceText("menuStartSimulation"));

        menuStepOnce.setText(getResourceText("menuStepOnce"));
        menuStepOnce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStepOnceActionPerformed(evt);
            }
        });

        menuStepMulti.setText(getResourceText("menuStepMulti"));
        menuStepMulti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStepMultiActionPerformed(evt);
            }
        });

        menuStartSimulation.add( menuStepOnce );
        menuStartSimulation.add(menuStepMulti);

        mtsMenuBar.add(menuStartSimulation);

        menuReports.setText(getResourceText("menuReports"));
        menuViewBusModel.setText(getResourceText("menuViewBusModel"));
        menuViewBusModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewBusModelActionPerformed(evt);
            }
        });

        menuViewTrainModel.setText(getResourceText("menuViewTrainModel"));
        menuViewTrainModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewTrainModelActionPerformed(evt);
            }
        });

        menuViewBuses.setText(getResourceText("menuViewBuses"));
        menuViewBuses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewBusesActionPerformed(evt);
            }
        });

        menuViewBusRoutes.setText(getResourceText("menuViewBusRoutes"));
        menuViewBusRoutes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewBusRoutesActionPerformed(evt);
            }
        });

        menuViewBusStops.setText(getResourceText("menuViewBusStops"));
        menuViewBusStops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewBusStopsActionPerformed(evt);
            }
        });

        menuViewTrains.setText(getResourceText("menuViewTrains"));
        menuViewTrains.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewTrainsActionPerformed(evt);
            }
        });

        menuViewTrainRoutes.setText(getResourceText("menuViewTrainRoutes"));
        menuViewTrainRoutes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewTrainRoutesActionPerformed(evt);
            }
        });

        menuViewTrainStops.setText(getResourceText("menuViewTrainStops"));
        menuViewTrainStops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewTrainStopsActionPerformed(evt);
            }
        });

        menuReports.add(menuViewBusModel);
        menuReports.add(menuViewTrainModel);
        menuReports.add(menuViewBusRoutes);
        menuReports.add(menuViewBusStops);
        menuReports.add(menuViewBuses);
        menuReports.add(menuViewTrainRoutes);
        menuReports.add(menuViewTrainStops);
        menuReports.add(menuViewTrains);


        mtsMenuBar.add(menuReports);

        setJMenuBar(mtsMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelAddBus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelAddTrain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                //.addGap(18, 18, 18)
                                .addComponent(panelAddBusStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                //.addGap(2, 2, 2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelAddBusRoute, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelAddTrainStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelAddTrainRoute, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelAddBusRider, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelAddTrainRider, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelExtendBusRoute, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelExtendTrainRoute, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelRandomBusRiders, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelRandomTrainRiders, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelAddBusTravelTimeFactor, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(214, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(panelAddBus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(panelAddTrain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap(186, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(panelAddTrainRoute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(panelAddBusRoute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(panelAddTrainStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(panelAddBusStop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(panelAddBusRider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelAddTrainRider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelExtendBusRoute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelExtendTrainRoute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelRandomBusRiders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelRandomTrainRiders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelAddBusTravelTimeFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );


        pack();
    }// </editor-fold>

    private void menuSimConditionsMouseClicked( java.awt.event.MouseEvent evt) {

        if( panelAddTrain.isVisible() ) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible() ) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }

        busTtFactorRouteIdComboBox.removeAllItems();
        busTtFactorStopIdComboBox.removeAllItems();

        srcBusStopIdComboBox.removeAllItems();
        destBusStopIdComboBox.removeAllItems();
        busRouteIdComboBox.removeAllItems();

        srcTrainStopIdComboBox.removeAllItems();
        destTrainStopIdComboBox.removeAllItems();
        trainRouteIdComboBox.removeAllItems();


    }

    private void menuSimFactorsActionPerformed(java.awt.event.ActionEvent evt) {
        JTextField busTrafficFactorField = new JTextField(5);
        JTextField busSpeedFactorField = new JTextField(5);
        JTextField trainSpeedFactorField = new JTextField(5);

        String busTrafficFactValue = String.valueOf( String.format(getResourceText("factorDecimalFormat"),
                simDriver.getBustrafficFactor()) );
        busTrafficFactorField.setText( busTrafficFactValue );

        String busSpeedFactValue = String.valueOf( String.format(getResourceText("factorDecimalFormat"),
                simDriver.getBusspeedFactor()) );
        busSpeedFactorField.setText( busSpeedFactValue );

        String trainSpeedFactValue = String.valueOf(  String.format(getResourceText("factorDecimalFormat"),
                simDriver.getTrainSpeedFactor()) );
        trainSpeedFactorField.setText( trainSpeedFactValue );

        JPanel trafficFactorPanel = new JPanel();
        trafficFactorPanel.setLayout(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel(getResourceText("busTrafficFactorLabel"), SwingConstants.RIGHT));
        labels.add(new JLabel(getResourceText("busSpeedFactorLabel"), SwingConstants.RIGHT));
        labels.add(new JLabel(getResourceText("busTrainSpeedFactorLabel"), SwingConstants.RIGHT));
        trafficFactorPanel.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        controls.add(busTrafficFactorField);
        controls.add(busSpeedFactorField);
        controls.add(trainSpeedFactorField);
        trafficFactorPanel.add(controls, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, trafficFactorPanel,
                getResourceText("simulationFactorsLabel"), JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String busSpeedFactText = busSpeedFactorField.getText().trim();
            String busTrafficFactText = busTrafficFactorField.getText().trim();
            String trainSpeedFactText = trainSpeedFactorField.getText().trim();

            if(busSpeedFactText.equals(EMPTY)) {
                JOptionPane.showMessageDialog(null, getResourceText("busSpeedFactorError"));
            } else if( busTrafficFactText.equals(EMPTY)) {
                JOptionPane.showMessageDialog(null, getResourceText("busTrafficFactorError"));
            } else if( trainSpeedFactText.equals(EMPTY)) {
                JOptionPane.showMessageDialog(null, getResourceText("trainSpeedFactorError"));
            } else {
                double busSpeedFactDbl = Double.parseDouble(busSpeedFactText);
                double busTrafficFactDbl = Double.parseDouble(busTrafficFactText);
                double trainSpeedFactDbl = Double.parseDouble(trainSpeedFactText);
                if( busSpeedFactDbl < 0 ) {
                    JOptionPane.showMessageDialog(null, getResourceText("incorrectBusSpeedFactorError"));
                } else if( busTrafficFactDbl < 0 ) {
                    JOptionPane.showMessageDialog(null, getResourceText("incorrectBusTrafficFactorError"));
                } else if( trainSpeedFactDbl < 0 ) {
                    JOptionPane.showMessageDialog(null, getResourceText("incorrectTrainSpeedFactorError"));
                } else {
                    simDriver.setBusspeedFactor(busSpeedFactDbl);
                    simDriver.setBustrafficFactor(busTrafficFactDbl);
                    simDriver.setTrainSpeedFactor(trainSpeedFactDbl);
                }
            }
        }
    }

    private void menuViewBusModelActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Process p = Runtime.getRuntime().exec(getResourceText("graphVizBusGraphCommand") );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, getResourceText("unableToShowGraphError"));
        }
    }

    private void menuViewTrainModelActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Process p = Runtime.getRuntime().exec(getResourceText("graphVizTrainGraphCommand") );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, getResourceText("unableToShowGraphError"));
        }
    }

    private void menuViewBusesActionPerformed(java.awt.event.ActionEvent evt) {

        HashMap<Integer,Bus> busMap = simDriver.getBuses();
        int noOfBuses = busMap.size();
        int size = noOfBuses == 0  ? 1 : noOfBuses;

        JDialog d1 = new JDialog(this, getResourceText("tblBusesDlgTitle"), false);
        d1.setLayout(new FlowLayout());
        d1.setSize(500,500);

        String[] columns = new String[] { getResourceText("tblBusesColId"), getResourceText("tblBusesColRouteId"),
                getResourceText("tblBusRoutesColRouteName"),
                getResourceText("tblBusesColCapacity"), getResourceText("tblBusesColSpeed"), getResourceText("tblBusesColPassengers") };

        String[][] data = new String [size][6];
        int i = 0;
        for( Map.Entry<Integer,Bus> tmpEntry: busMap.entrySet() ) {
            data[i][0] = String.valueOf(tmpEntry.getValue().getID());
            data[i][1] = String.valueOf(tmpEntry.getValue().getRouteID());
            data[i][2] = simDriver.getBusRoutes().get( tmpEntry.getValue().getRouteID() ).getName();
            data[i][3] = String.valueOf(tmpEntry.getValue().getCapacity());
            data[i][4] = String.valueOf(tmpEntry.getValue().getSpeed());
            data[i][5] = String.valueOf(tmpEntry.getValue().getNumberOfPassengers());
            i+=1;
        }
        JTable jt=new JTable(data,columns);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        d1.add(sp);
        d1.setVisible(true);
    }

    private void menuViewBusRoutesActionPerformed(java.awt.event.ActionEvent evt) {
        HashMap<Integer,BusRoute> busRouteMap = simDriver.getBusRoutes();
        int noOfBusRoutes = busRouteMap.size();
        int size = noOfBusRoutes == 0  ? 1 : noOfBusRoutes;

        JDialog d1 = new JDialog(this, getResourceText("tblBusRoutesDlgTitle"), false);
        d1.setLayout(new FlowLayout());
        d1.setSize(500,500);

        String[] columns = new String[] { getResourceText("tblBusRoutesColId"), getResourceText("tblBusRoutesColRouteNo"),
                getResourceText("tblBusRoutesColRouteName"),
                getResourceText("tblBusRoutesColNoOfStops"), getResourceText("tblBusRoutesColStopNames") };

        String[][] data = new String [size][5];
        int i = 0;
        for( Map.Entry<Integer,BusRoute> tmpEntry: busRouteMap.entrySet() ) {
            data[i][0] = String.valueOf(tmpEntry.getValue().getID());
            data[i][1] = String.valueOf(tmpEntry.getValue().getNumber());
            data[i][2] = String.valueOf(tmpEntry.getValue().getName());
            data[i][3] = String.valueOf(tmpEntry.getValue().getLength());
            StringBuilder stopListBuilder = new StringBuilder();
            if( tmpEntry.getValue().getLength() > 0 ) {
                for( int[] stopIds : tmpEntry.getValue().stopsOnRoute.values() )
                {
                    stopListBuilder.append( simDriver.getBusStops().get( stopIds[0] ).getName() ).append(",");
                }
            }
            data[i][4] = String.valueOf(stopListBuilder.toString());
            i+=1;
        }
        JTable jt=new JTable(data,columns);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        d1.add(sp);
        d1.setVisible(true);
    }

    private void menuViewBusStopsActionPerformed(java.awt.event.ActionEvent evt) {
        HashMap<Integer,BusStop> busStopMap = simDriver.getBusStops();
        int noOfBusStops = busStopMap.size();
        int size = noOfBusStops == 0  ? 1 : noOfBusStops;

        JDialog d1 = new JDialog(this, getResourceText("tblBusStopsDlgTitle"), false);
        d1.setLayout(new FlowLayout());
        d1.setSize(500,500);

        String[] columns = new String[] { getResourceText("tblBusStopsColId"), getResourceText("tblBusStopsColName"),
                getResourceText("tblBusStopsColXCoord"), getResourceText("tblBusStopsColYCoord"), getResourceText("tblBusStopsColNoOfRiders") };

        String[][] data = new String [size][5];
        int i = 0;
        for( Map.Entry<Integer,BusStop> tmpEntry: busStopMap.entrySet() ) {
            data[i][0] = String.valueOf(tmpEntry.getValue().getID());
            data[i][1] = String.valueOf(tmpEntry.getValue().getName());
            data[i][2] = String.valueOf(tmpEntry.getValue().getXCoord());
            data[i][3] = String.valueOf(tmpEntry.getValue().getYCoord());
            data[i][4] = String.valueOf(tmpEntry.getValue().getNumberOfRiders() );
            i+=1;
        }
        JTable jt=new JTable(data,columns);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        d1.add(sp);
        d1.setVisible(true);
    }


    private void menuViewTrainsActionPerformed(java.awt.event.ActionEvent evt) {

        HashMap<Integer,Train> trainMap = simDriver.getTrains();
        int noOfTrains = trainMap.size();
        int size = noOfTrains == 0  ? 1 : noOfTrains;

        JDialog d1 = new JDialog(this, getResourceText("tblTrainsDlgTitle"), false);
        d1.setLayout(new FlowLayout());
        d1.setSize(500,500);

        String[] columns = new String[] { getResourceText("tblTrainsColId"), getResourceText("tblTrainsColRouteId"),
                getResourceText("tblTrainRoutesColRouteName"),
                getResourceText("tblTrainsColCapacity"), getResourceText("tblTrainsColSpeed"), getResourceText("tblTrainsColPassengers")};

        String[][] data = new String [size][6];
        int i = 0;
        for( Map.Entry<Integer,Train> tmpEntry: trainMap.entrySet() ) {
            data[i][0] = String.valueOf(tmpEntry.getValue().getID());
            data[i][1] = String.valueOf(tmpEntry.getValue().getRouteID());
            data[i][2] = simDriver.getTrainRoutes().get( tmpEntry.getValue().getRouteID() ).getName();
            data[i][3] = String.valueOf(tmpEntry.getValue().getCapacity());
            data[i][4] = String.valueOf(tmpEntry.getValue().getSpeed());
            data[i][5] = String.valueOf(tmpEntry.getValue().getNumberOfPassengers());
            i+=1;
        }
        JTable jt=new JTable(data,columns);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        d1.add(sp);
        d1.setVisible(true);
    }

    private void menuViewTrainRoutesActionPerformed(java.awt.event.ActionEvent evt) {
        HashMap<Integer,TrainRoute> trainRouteMap = simDriver.getTrainRoutes();
        int noOfTrainRoutes = trainRouteMap.size();
        int size = noOfTrainRoutes == 0  ? 1 : noOfTrainRoutes;

        JDialog d1 = new JDialog(this, getResourceText("tblTrainRoutesDlgTitle"), false);
        d1.setLayout(new FlowLayout());
        d1.setSize(500,500);

        String[] columns = new String[] {  getResourceText("tblTrainRoutesColId"), getResourceText("tblTrainRoutesColRouteNo"),
                getResourceText("tblTrainRoutesColRouteName"), getResourceText("tblTrainRoutesColNoOfStops"), getResourceText("tblTrainRoutesColStopNames") };

        String[][] data = new String [size][5];
        int i = 0;
        for( Map.Entry<Integer,TrainRoute> tmpEntry: trainRouteMap.entrySet() ) {
            data[i][0] = String.valueOf(tmpEntry.getValue().getID());
            data[i][1] = String.valueOf(tmpEntry.getValue().getNumber());
            data[i][2] = String.valueOf(tmpEntry.getValue().getName());
            data[i][3] = String.valueOf(tmpEntry.getValue().getLength());
            StringBuilder stopListBuilder = new StringBuilder();
            if( tmpEntry.getValue().getLength() > 0 ) {
                for( int[] stopIds : tmpEntry.getValue().stopsOnRoute.values() )
                {
                    stopListBuilder.append( simDriver.getTrainStops().get( stopIds[0] ).getName() ).append(",");
                }
            }
            data[i][4] = String.valueOf(stopListBuilder.toString());
            i+=1;
        }
        JTable jt=new JTable(data,columns);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        d1.add(sp);
        d1.setVisible(true);
    }

    private void menuViewTrainStopsActionPerformed(java.awt.event.ActionEvent evt) {
        HashMap<Integer,TrainStop> trainStopMap = simDriver.getTrainStops();
        int noOfTrainStops = trainStopMap.size();
        int size = noOfTrainStops == 0  ? 1 : noOfTrainStops;

        JDialog d1 = new JDialog(this, getResourceText("tblTrainStopsDlgTitle"), false);
        d1.setLayout(new FlowLayout());
        d1.setSize(500,500);

        String[] columns = new String[] {  getResourceText("tblTrainStopsColId"), getResourceText("tblTrainStopsColName"),
                getResourceText("tblTrainStopsColXCoord"), getResourceText("tblTrainStopsColYCoord"), getResourceText("tblTrainStopsColNoOfRiders") };

        String[][] data = new String [size][5];
        int i = 0;
        for( Map.Entry<Integer,TrainStop> tmpEntry: trainStopMap.entrySet() ) {
            data[i][0] = String.valueOf(tmpEntry.getValue().getID());
            data[i][1] = String.valueOf(tmpEntry.getValue().getName());
            data[i][2] = String.valueOf(tmpEntry.getValue().getXCoord());
            data[i][3] = String.valueOf(tmpEntry.getValue().getYCoord());
            data[i][4] = String.valueOf(tmpEntry.getValue().getNumberOfRiders() );
            i+=1;
        }
        JTable jt=new JTable(data,columns);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        d1.add(sp);
        d1.setVisible(true);
    }

    private void menuFileCommandsActionPerformed(java.awt.event.ActionEvent evt) {
        hideAllPanels();
        int retVal = fileChooser.showOpenDialog(this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String commandFile = selectedFile.getAbsolutePath();
            if( null != commandFile && !commandFile.trim().isEmpty() ) {
                clearMtsSystemData();
                System.setOut( psOut );
                System.setErr( psOut );
                consoleLogsDialog.setTitle( getResourceText("uploadSimulationCommandsDialogTitle") );
                consoleLogsDialog.setVisible(true);
                movingBusLabel.setVisible(true);
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                executorService.execute(new Runnable() {
                    public void run() {
                        try (Stream<String> linesInFile = Files.lines(Paths.get(commandFile))) {
                            linesInFile.forEach( line -> simDriver.processTokens(line.split(",")) );
                            linesInFile.close();
                        } catch (IOException e) {
                            out.println("Error Processing Command");
                        }
                        movingBusLabel.setVisible(false);
                        JOptionPane.showMessageDialog(null, getResourceText("commandsProcessingCompleteMsg"));
                    }
                });
                executorService.shutdown();
            }
        }
    }

    /**
     *
     * @param evt
     */
    private void menuStepOnceActionPerformed(java.awt.event.ActionEvent evt) {
        movingBusLabel.setVisible(true);
        consoleLogsDialog.setVisible(true);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            public void run() {
                simDriver.stepOnce();
                movingBusLabel.setVisible(false);
                JOptionPane.showMessageDialog(null, getResourceText("simulationCompleteMsg"));
            }
        });
        executorService.shutdown();
    }

    /**
     *
     * @param evt
     */
    private void menuStepMultiActionPerformed(java.awt.event.ActionEvent evt) {

        JTextField numOfStepsField = new JTextField(5);
        JTextField frequencyField = new JTextField(5);
        JTextField waitInSecondsField = new JTextField(5);
        JTextField graphVizFrequencyField = new JTextField(5);

        numOfStepsField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        frequencyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        waitInSecondsField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        graphVizFrequencyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if( !( (c >= '0' && c <= '9') || c == evt.VK_BACK_SPACE || c == evt.VK_DELETE ) ) {
                    evt.consume();
                }
            }
        });

        JPanel stepMultiPanel = new JPanel();
        stepMultiPanel.setLayout(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel(getResourceText("startSimNoOfStepsMessage"), SwingConstants.RIGHT));
        labels.add(new JLabel(getResourceText("startSimFrequencyLabel"), SwingConstants.RIGHT));
        labels.add(new JLabel(getResourceText("startSimWaitInSecondsLabel"), SwingConstants.RIGHT));
        labels.add(new JLabel(getResourceText("displayModelFrequencyLabel"), SwingConstants.RIGHT));
        stepMultiPanel.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        controls.add(numOfStepsField);
        controls.add(frequencyField);
        controls.add(waitInSecondsField);
        controls.add(graphVizFrequencyField);
        stepMultiPanel.add(controls, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, stepMultiPanel, getResourceText("startSimDialogTitle"), JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String numOfSteps = numOfStepsField.getText().trim();
            String frequency = frequencyField.getText().trim();
            String waitInSeconds = waitInSecondsField.getText().trim();
            String graphVizFrequency = graphVizFrequencyField.getText().trim();
            PrintStream standardOut = out;

            if(numOfSteps.equals(EMPTY)) {
                JOptionPane.showMessageDialog(null, getResourceText("startSimEnterStepsError"));
            } else {
                int noOfSteps = Integer.parseInt( numOfSteps );
                if(noOfSteps == 0 ) {
                    JOptionPane.showMessageDialog(null, "Steps can not be zero");
                } else {
                    consoleLogsDialog.setTitle( getResourceText("stepMultiSimulationDialogTitle"));
                    if( noOfSteps == 1 ) {
                        movingBusLabel.setVisible(true);
                        consoleLogsDialog.setVisible(true);
                        ExecutorService executorService = Executors.newFixedThreadPool(2);
                        executorService.execute(new Runnable() {
                            public void run() {
                                simDriver.stepOnce();
                                movingBusLabel.setVisible(false);
                                JOptionPane.showMessageDialog(null, getResourceText("simulationCompleteMsg"));
                            }
                        });
                        executorService.shutdown();
                    } else {
                        if( frequency.equals(EMPTY) && waitInSeconds.equals(EMPTY) && graphVizFrequency.equals(EMPTY) ) {
                            movingBusLabel.setVisible(true);
                            consoleLogsDialog.setVisible(true);
                            ExecutorService executorService = Executors.newFixedThreadPool(2);
                            executorService.execute(new Runnable() {
                                public void run() {
                                    simDriver.stepMulti(2, noOfSteps, -1, -1, -1);
                                    JOptionPane.showMessageDialog(null, getResourceText("simulationCompleteMsg"));
                                    movingBusLabel.setVisible(false);
                                }
                            });
                            executorService.shutdown();
                        } else if( !frequency.equals(EMPTY) && waitInSeconds.equals(EMPTY) && graphVizFrequency.equals(EMPTY) ) {
                            movingBusLabel.setVisible(true);
                            consoleLogsDialog.setVisible(true);
                            ExecutorService executorService = Executors.newFixedThreadPool(2);
                            executorService.execute(new Runnable() {
                                public void run() {
                                    simDriver.stepMulti(3, noOfSteps, Integer.parseInt(frequency), -1, -1);
                                    JOptionPane.showMessageDialog(null, getResourceText("simulationCompleteMsg"));
                                    movingBusLabel.setVisible(false);
                                }
                            });
                            executorService.shutdown();
                        } else if( !frequency.equals(EMPTY) && !waitInSeconds.equals(EMPTY) && graphVizFrequency.equals(EMPTY) ) {
                            movingBusLabel.setVisible(true);
                            consoleLogsDialog.setVisible(true);
                            ExecutorService executorService = Executors.newFixedThreadPool(2);
                            executorService.execute(new Runnable() {
                                public void run() {
                                    simDriver.stepMulti(4, noOfSteps, Integer.parseInt(frequency), Integer.parseInt(waitInSeconds), -1);
                                    JOptionPane.showMessageDialog(null, getResourceText("simulationCompleteMsg"));
                                    movingBusLabel.setVisible(false);
                                }
                            });
                            executorService.shutdown();
                        } else if( !frequency.equals(EMPTY) && !waitInSeconds.equals(EMPTY) && !graphVizFrequency.equals(EMPTY) ) {
                            movingBusLabel.setVisible(true);
                            consoleLogsDialog.setVisible(true);
                            ExecutorService executorService = Executors.newFixedThreadPool(2);
                            executorService.execute(new Runnable() {
                                public void run() {
                                    simDriver.stepMulti(5, noOfSteps, Integer.parseInt(frequency), Integer.parseInt(waitInSeconds), Integer.parseInt(graphVizFrequency));
                                    JOptionPane.showMessageDialog(null, getResourceText("simulationCompleteMsg"));
                                    movingBusLabel.setVisible(false);
                                }
                            });
                            executorService.shutdown();
                            openDisplayModels();
                        } else {
                            JOptionPane.showMessageDialog(null, getResourceText("canNotStartSimulationError"));
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param evt
     */
    private void menuUploadDataFromDbActionPerformed(java.awt.event.ActionEvent evt) {
        hideAllPanels();
        clearMtsSystemData();
        System.setOut( psOut );
        System.setErr( psOut );
        consoleLogsDialog.setTitle( getResourceText("uploadDataFromDBDialogTitle"));
        consoleLogsDialog.setVisible(true);
        movingBusLabel.setVisible(true);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            public void run() {
                int result = simDriver.uploadMARTAData();
                movingBusLabel.setVisible(false);
                if( result == 0 ) {
                    JOptionPane.showMessageDialog(null, getResourceText("dbDataUploadSuccessMsg"));
                } else {
                    JOptionPane.showMessageDialog(null, getResourceText("dbDataUploadFailedMsg"));
                }
            }
        });
        executorService.shutdown();
    }

    /**
     * Clear MTS data on every data upload
     */
    public void clearMtsSystemData() {
        simDriver.getBusRoutes().clear();
        simDriver.getBusStops().clear();
        simDriver.getBuses().clear();
        simDriver.getTrainRoutes().clear();
        simDriver.getTrainStops().clear();
        simDriver.getTrains().clear();
    }

    private void btnAddTrainRidersActionPerformed(java.awt.event.ActionEvent evt) {
        Integer srcStopId = (Integer) srcTrainStopIdComboBox.getSelectedItem();
        Integer destStopId = (Integer) destTrainStopIdComboBox.getSelectedItem();
        Integer routeId = (Integer) trainRouteIdComboBox.getSelectedItem();
        if( null == srcStopId ) {
            JOptionPane.showMessageDialog(null, getResourceText("addTrainRiderSrcStopNotSelectedError") );
        } else if( null == destStopId ) {
            JOptionPane.showMessageDialog(null, getResourceText("addTrainRiderDestStopNotSelectedError") );
        } else if( null == routeId ) {
            JOptionPane.showMessageDialog(null, getResourceText("addTrainRiderRouteIdNotSelectedError") );
        } else if( noOfTrainRiders.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("addTrainRiderNoOfRidersNotEnteredError") );
        } else {
            Integer noOfRiders = Integer.parseInt( noOfTrainRiders.getText() );
            simDriver.addRider("train", srcStopId, noOfRiders, destStopId, routeId );
            JOptionPane.showMessageDialog(null, noOfRiders + " riders added to train stop " + srcStopId);
            panelAddTrainRider.setVisible(false);
            noOfTrainRiders.setText(EMPTY);
        }
    }

    private void btnCloseAddTrainRidersActionPerformed(java.awt.event.ActionEvent evt) {
        panelAddTrainRider.setVisible(false);
    }

    private void btnAddBusRidersActionPerformed(java.awt.event.ActionEvent evt) {
        Integer srcStopId = (Integer) srcBusStopIdComboBox.getSelectedItem();
        Integer destStopId = (Integer) destBusStopIdComboBox.getSelectedItem();
        Integer routeId = (Integer) busRouteIdComboBox.getSelectedItem();
        if( null == srcStopId ) {
            JOptionPane.showMessageDialog(null, getResourceText("addBusRiderSrcStopNotSelectedError") );
        } else if( null == destStopId ) {
            JOptionPane.showMessageDialog(null, getResourceText("addBusRiderDestStopNotSelectedError") );
        } else if( null == routeId ) {
            JOptionPane.showMessageDialog(null, getResourceText("addBusRiderRouteIdNotSelectedError") );
        } else if( noOfBusRiders.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("addBusRiderNoOfRidersNotEnteredError") );
        } else {
            Integer noOfRiders = Integer.parseInt( noOfBusRiders.getText() );
            simDriver.addRider("bus", srcStopId, noOfRiders, destStopId, routeId );
            JOptionPane.showMessageDialog(null, noOfRiders + " riders added to stop " + srcStopId);
            panelAddBusRider.setVisible(false);
            noOfBusRiders.setText(EMPTY);
        }
    }

    private void btnCloseAddBusRidersActionPerformed(java.awt.event.ActionEvent evt) {
        panelAddBusRider.setVisible(false);
    }

    private void btnAddBusActionPerformed(java.awt.event.ActionEvent evt) {
        if( busId.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busIdRequiredError"));
        } else if( null == busRouteId.getSelectedItem() ) {
            JOptionPane.showMessageDialog(null, getResourceText("busRouteRequiredError"));
        } else if( busLocation.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busStartLocationRequiredError"));
        } else if( busCapacity.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busCapacityRequiredError"));
        } else if( busSpeed.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busSpeedRequiredError"));
        } else {
            int inputBusId = Integer.parseInt(busId.getText());
            Bus resultBus = simDriver.getBuses().get(inputBusId);
            if( null == resultBus ) {
                int busID = simDriver.addBus(inputBusId, (int) busRouteId.getSelectedItem(),
                        Integer.parseInt(busLocation.getText()),
                        Integer.parseInt(busCapacity.getText()), Integer.parseInt(busSpeed.getText()));
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("busSuccessMsg"), busID));
                panelAddBus.setVisible(false);
                clearAddBusFields();
            } else {
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("busAlreadyExistsError"), inputBusId) );
            }
        }
    }

    private void menuAddBusMouseClicked(java.awt.event.MouseEvent evt) {
        panelAddBus.setVisible(true);
    }

    private void menuAddBusActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddTrain.isVisible() ) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible() ) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelAddBus.isVisible() ) {
            for( Integer tmpRouteId: simDriver.getBusRoutes().keySet() ) {
                busRouteId.addItem( tmpRouteId );
            }
            panelAddBus.setVisible(true);
        }

        addAddBusInputVerifiers(integerVerifier);

    }

    private void menuAddBusRidersActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddTrain.isVisible() ) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible() ) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBus.isVisible() ) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible()) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(true);

            for( Integer tmpBusStopId: simDriver.getBusStopIds() ) {
                srcBusStopIdComboBox.addItem(tmpBusStopId);
                destBusStopIdComboBox.addItem(tmpBusStopId);
            }
            for( Integer tmpBusRouteId: simDriver.getBusRouteIds() ) {
                busRouteIdComboBox.addItem(tmpBusRouteId);
            }
            addAddBusRiderVerifiers( integerVerifier );
        }
    }

    private void addAddBusRiderVerifiers( InputVerifier aTmpVerifier) {
        noOfBusRiders.setInputVerifier(aTmpVerifier);
    }

    private void menuAddTrainRidersActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddTrain.isVisible() ) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible() ) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBus.isVisible() ) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible()) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(true);

            for( Integer tmpTrainStopId: simDriver.getTrainStopIds() ) {
                srcTrainStopIdComboBox.addItem(tmpTrainStopId);
                destTrainStopIdComboBox.addItem(tmpTrainStopId);
            }
            for( Integer tmpTrainRouteId: simDriver.getTrainRouteIds() ) {
                trainRouteIdComboBox.addItem(tmpTrainRouteId);
            }
            addAddTrainRiderVerifiers( integerVerifier );
        }
    }

    private void menuBusTtFactorActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddTrain.isVisible() ) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible() ) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBus.isVisible() ) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible()) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(true);
        }
        if( !panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(true);

            for( Integer tmpBusRouteId: simDriver.getBusRouteIds() ) {
                busTtFactorRouteIdComboBox.addItem(tmpBusRouteId);
            }

            for( Integer tmpBusStopId: simDriver.getBusStopIds() ) {
                busTtFactorStopIdComboBox.addItem(tmpBusStopId);
            }
            busTtFactor.setInputVerifier( doubleVerifier );
        }
    }

    private void addAddTrainRiderVerifiers( InputVerifier aTmpVerifier) {
        noOfTrainRiders.setInputVerifier(aTmpVerifier);
    }

    private void addAddBusInputVerifiers( InputVerifier atmpVerifier) {
        busId.setInputVerifier( atmpVerifier );
        busLocation.setInputVerifier( atmpVerifier );
        busCapacity.setInputVerifier( atmpVerifier );
        busSpeed.setInputVerifier( atmpVerifier );
    }

    private void btnCloseAddBusPanelActionPerformed(java.awt.event.ActionEvent evt) {
        addAddBusInputVerifiers(null);
        clearAddBusFields();
        panelAddBus.setVisible(false);
    }

    private void clearAddBusFields() {
        busId.setText(EMPTY);
        busLocation.setText(EMPTY);
        busCapacity.setText(EMPTY);
        busSpeed.setText(EMPTY);
    }

    private void btnAddTrainActionPerformed(java.awt.event.ActionEvent evt) {
        if( uniqueTrainId.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainIdRequiredError"));
        } else if( null == trainRouteId.getSelectedItem() ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainRouteRequiredError"));
        } else if( trainInputLocation.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainStartLocationRequiredError"));
        } else if( trainCapacity.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainCapacityRequiredError"));
        } else if( trainSpeed.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainSpeedRequiredError"));
        } else {
            int inputTrainId = Integer.parseInt(uniqueTrainId.getText());
            Train resultTrain = simDriver.getTrains().get(inputTrainId);
            if( null == resultTrain ) {
                int trainID = simDriver.addTrain(inputTrainId, (int) (trainRouteId.getSelectedItem()), Integer.parseInt(trainInputLocation.getText()),
                        Integer.parseInt(trainCapacity.getText()), Integer.parseInt(trainSpeed.getText()));
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("trainSuccessMsg"), trainID));
                panelAddTrain.setVisible(false);
                clearAddTrainFields();
            } else {
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("trainAlreadyExistsError"), inputTrainId));
            }
        }
    }

    private void clearAddTrainFields() {
        uniqueTrainId.setText(EMPTY);
        trainInputLocation.setText(EMPTY);
        trainCapacity.setText(EMPTY);
        trainSpeed.setText(EMPTY);
    }

    private void btnCloseAddTrainPanelActionPerformed(java.awt.event.ActionEvent evt) {
        addAddTrainInputVerifiers(null);
        clearAddTrainFields();
        panelAddTrain.setVisible(false);
    }

    private void menuAddTrainActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddTrainStop.isVisible()) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible()) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelAddTrain.isVisible()) {
            for( Integer tmpRouteId: simDriver.getTrainRoutes().keySet() ) {
                trainRouteId.addItem(tmpRouteId);
            }
            panelAddTrain.setVisible(true);
        }
        addAddTrainInputVerifiers(integerVerifier);
    }

    private void addAddTrainInputVerifiers(InputVerifier atmpVerifier) {
        uniqueTrainId.setInputVerifier( atmpVerifier );
        trainInputLocation.setInputVerifier( atmpVerifier );
        trainCapacity.setInputVerifier( atmpVerifier );
        trainSpeed.setInputVerifier( atmpVerifier );
    }

    private void btnAddTrainStopActionPerformed(java.awt.event.ActionEvent evt) {
        if( uniqueTrainStopId.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainStopIdRequiredError"));
        } else if( trainStopName.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainStopNameRequiredError"));
        } else if( trainStopLatitude.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainStopLatitudeRequiredError"));
        } else if( trainStopLongitude.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainStopLongitudeRequiredError"));
        } else {
            int inputTrainStopId = Integer.parseInt(uniqueTrainStopId.getText());
            TrainStop resultTrainStop = simDriver.getTrainStops().get(inputTrainStopId);
            if( null == resultTrainStop ) {
                int trainStopID = simDriver.addTrainStop(inputTrainStopId, trainStopName.getText(), Double.parseDouble(trainStopLatitude.getText()),
                        Double.parseDouble(trainStopLongitude.getText()));
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("trainStopSuccessMsg"), trainStopID));
                panelAddTrainStop.setVisible(false);
                clearAddTrainStopFields();
            } else {
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("trainStopAlreadyExistsError"),
                        inputTrainStopId));
            }
        }
    }

    private void clearAddTrainStopFields() {
        uniqueTrainStopId.setText(EMPTY);
        trainStopName.setText(EMPTY);
        trainStopLatitude.setText(EMPTY);
        trainStopLongitude.setText(EMPTY);
    }

    private void btnCloseAddTrainStopPanelActionPerformed(java.awt.event.ActionEvent evt) {
        panelAddTrainStop.setVisible(false);
    }

    private void menuAddBusStopActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddTrain.isVisible()) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible() ) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelAddBusStop.isVisible() ) {
            busStopLongitude.setInputVerifier( doubleVerifier );
            busStopLatitude.setInputVerifier(doubleVerifier);
            panelAddBusStop.setVisible(true);
        }
    }

    private void btnAddBusRouteActionPerformed(java.awt.event.ActionEvent evt) {
        if( uniqueBusRouteId.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busRouteIdRequiredError"));
        } else if( busRouteNumber.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busRouteNumberRequiredError"));
        } else if( busRouteName.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busRouteNameRequiredError"));
        } else {
            int inputBusRouteId = Integer.parseInt(uniqueBusRouteId.getText());
            BusRoute resultBusRoute = simDriver.getBusRoutes().get(inputBusRouteId);
            if( null == resultBusRoute ) {
                int busRouteID = simDriver.addBusRoute(inputBusRouteId, Integer.parseInt(busRouteNumber.getText()), busRouteName.getText());
                JOptionPane.showMessageDialog(null, MessageFormat.format( getResourceText("busRouteSuccessMsg"), busRouteID));
                panelAddBusRoute.setVisible(false);
                uniqueBusRouteId.setText(EMPTY);
                busRouteNumber.setText(EMPTY);
                busRouteName.setText(EMPTY);
            } else {
                JOptionPane.showMessageDialog(null, MessageFormat.format( getResourceText("busRouteAlreadyExistsError"),
                        inputBusRouteId));
            }
        }

    }

    private void btnCloseAddBusRoutePanelActionPerformed(java.awt.event.ActionEvent evt) {
        panelAddBusRoute.setVisible(false);
    }

    private void menuAddBusRouteActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddTrain.isVisible()) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible()) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible()) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelAddBusRoute.isVisible() ) {
            panelAddBusRoute.setVisible(true);
        }
    }

    private void btnAddBusStopActionPerformed(java.awt.event.ActionEvent evt) {
        if( uniqueBusStopId.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busStopIdRequiredError"));
        } else if( nameOfBusStop.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busStopNameRequiredError"));
        } else if( busStopLatitude.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busStopLatitudeRequiredError"));
        } else if( busStopLongitude.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("busStopLongitudeRequiredError"));
        } else {
            int inputBusStopId = Integer.parseInt(uniqueBusStopId.getText());
            BusStop resultBusStop = simDriver.getBusStops().get(inputBusStopId);
            if( null == resultBusStop ) {
                int busStopID = simDriver.addBusStop(inputBusStopId, nameOfBusStop.getText(), Double.parseDouble(busStopLatitude.getText()),
                        Double.parseDouble(busStopLongitude.getText()));
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("busStopSucessMsg"), busStopID));
                panelAddBusStop.setVisible(false);
                uniqueBusStopId.setText(EMPTY);
                nameOfBusStop.setText(EMPTY);
                busStopLatitude.setText(EMPTY);
                busStopLongitude.setText(EMPTY);
            } else {
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("busStopAlreadyExistsError"), inputBusStopId));
            }
        }

    }

    private void btnCloseAddBusStopPanelActionPerformed(java.awt.event.ActionEvent evt) {
        panelAddBusStop.setVisible(false);
    }

    private void menuAddTrainStopActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddTrain.isVisible()) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible()) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(true);
        }
    }

    private void btnAddTrainRouteActionPerformed(java.awt.event.ActionEvent evt) {
        if( uniqueTrainRouteId.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainRouteIdRequiredError"));
        } else if( trainRouteNumber.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainRouteNumberRequiredError"));
        } else if( trainRouteName.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("trainRouteNameRequiredError"));
        } else {
            int inputTrainRouteId = Integer.parseInt(uniqueTrainRouteId.getText());
            TrainRoute resultTrainRoute = simDriver.getTrainRoutes().get(inputTrainRouteId);
            if( null == resultTrainRoute ) {
                int trainRouteID = simDriver.addTrainRoute(inputTrainRouteId, Integer.parseInt(trainRouteNumber.getText()), trainRouteName.getText());
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("trainRouteSuccessMsg"), trainRouteID));
                panelAddTrainRoute.setVisible(false);
                uniqueTrainRouteId.setText(EMPTY);
                trainRouteNumber.setText(EMPTY);
                trainRouteName.setText(EMPTY);
            } else {
                JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("trainRouteAlreadyExistsError"), inputTrainRouteId));
            }
        }
    }

    private void btnCloseAddTrainRoutePanelActionPerformed(java.awt.event.ActionEvent evt) {
        panelAddTrainRoute.setVisible(false);
    }

    private void menuAddTrainRouteActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddTrain.isVisible()) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelAddTrainRoute.isVisible() ) {
            panelAddTrainRoute.setVisible(true);
        }
    }

    private void menuExtendBusRouteActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddTrain.isVisible()) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelExtendBusRoute.isVisible()) {
            panelExtendBusRoute.setVisible(true);
            for( Integer tmpBusStopId: simDriver.getBusStopIds() ) {
                extendBusRouteBusStopComboBox.addItem(tmpBusStopId);
            }
            for( Integer tmpBusRouteId: simDriver.getBusRouteIds() ) {
                extendBusRouteBusRouteIdComboBox.addItem(tmpBusRouteId);
            }
            avgBusTravelTime.setInputVerifier( integerVerifier );
        }
    }

    private void btnExtendBusRouteActionPerformed(java.awt.event.ActionEvent evt) {
        Integer routeId = (Integer) extendBusRouteBusRouteIdComboBox.getSelectedItem();
        Integer stopId = (Integer) extendBusRouteBusStopComboBox.getSelectedItem();

        if( null == routeId ) {
            JOptionPane.showMessageDialog(null, getResourceText("extendBusRouteRouteIdNotSelectedError"));
        } else if( null == stopId) {
            JOptionPane.showMessageDialog(null, getResourceText("extendBusRouteStopIdNotSelectedError"));
        } else if( avgBusTravelTime.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("extendBusRouteAvgTravelTimeNotSpecifiedError"));
        } else {
            int avgTravelTime = Integer.parseInt( avgBusTravelTime.getText() );
            simDriver.extendBusRoute(routeId, stopId, avgTravelTime);
            panelExtendBusRoute.setVisible(false);
        }
    }

    private void btnCloseExtendBusRouteActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
    }

    private void menuBusSystemMouseClicked(java.awt.event.MouseEvent evt) {
        if(panelExtendBusRoute.isVisible()) {
            panelExtendBusRoute.setVisible(false);
        }

        busRouteId.removeAllItems();
        extendBusRouteBusStopComboBox.removeAllItems();
        extendBusRouteBusRouteIdComboBox.removeAllItems();
    }

    private void menuExtendTrainRouteActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible() ) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddTrain.isVisible()) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRoute.isVisible() ) {
            panelAddTrainRoute.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelExtendTrainRoute.isVisible()) {
            panelExtendTrainRoute.setVisible(true);
            for( Integer tmpTrainStopId: simDriver.getTrainStopIds() ) {
                extendTrainRouteTrainStopComboBox.addItem(tmpTrainStopId);
            }
            for( Integer tmpTrainRouteId: simDriver.getTrainRouteIds() ) {
                extendTrainRouteTrainRouteIdComboBox.addItem(tmpTrainRouteId);
            }
            avgTrainTravelTime.setInputVerifier( integerVerifier );
        }
    }

    private void btnExtendTrainRouteActionPerformed(java.awt.event.ActionEvent evt) {
        Integer routeId = (Integer) extendTrainRouteTrainRouteIdComboBox.getSelectedItem();
        Integer stopId = (Integer) extendTrainRouteTrainStopComboBox.getSelectedItem();

        if( null == routeId ) {
            JOptionPane.showMessageDialog(null, getResourceText("extendTrainRouteRouteIdNotSelectedError"));
        } else if( null == stopId) {
            JOptionPane.showMessageDialog(null, getResourceText("extendTrainRouteStopIdNotSelectedError"));
        } else if( avgTrainTravelTime.getText().equals(EMPTY) ) {
            JOptionPane.showMessageDialog(null, getResourceText("extendTrainRouteAvgTravelTimeNotSpecifiedError"));
        } else {
            int avgTravelTime = Integer.parseInt( avgTrainTravelTime.getText() );
            simDriver.extendTrainRoute(routeId, stopId, avgTravelTime);
        }
    }

    private void btnCloseExtendTrainRouteActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
    }

    private void menuTrainSystemMouseClicked(java.awt.event.MouseEvent evt) {
        if( panelExtendTrainRoute.isVisible() ) {
            panelExtendTrainRoute.setVisible(false);
        }
        trainRouteId.removeAllItems();
        extendTrainRouteTrainStopComboBox.removeAllItems();
        extendTrainRouteTrainRouteIdComboBox.removeAllItems();
    }

    private void btnAddRandomBusRidersActionPerformed(java.awt.event.ActionEvent evt) {
        if( minBusRiders.getText().equals(EMPTY)) {
            JOptionPane.showMessageDialog(null, getResourceText("minBusRidersRequiredError"));
        } else if( maxBusRiders.getText().equals(EMPTY)) {
            JOptionPane.showMessageDialog(null, getResourceText("maxBusRidersRequiredError"));
        } else {
           int minVal = Integer.parseInt(minBusRiders.getText());
           int maxVal = Integer.parseInt(maxBusRiders.getText());
           int ridersCreated= simDriver.createRandomBusRiders( minVal, maxVal );
           JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("busRidersCreatedMsg"), ridersCreated));
        }
    }

    private void btnCloseRandomBusRidersActionPerformed(java.awt.event.ActionEvent evt) {
        panelRandomBusRiders.setVisible(false);
    }

    private void btnAddRandomTrainRidersActionPerformed(java.awt.event.ActionEvent evt) {
        if( minTrainRiders.getText().equals(EMPTY)) {
            JOptionPane.showMessageDialog(null, getResourceText("minTrainRidersRequiredError"));
        } else if( maxTrainRiders.getText().equals(EMPTY)) {
            JOptionPane.showMessageDialog(null, getResourceText("maxTrainRidersRequiredError"));
        } else {
            int minVal = Integer.parseInt(minTrainRiders.getText());
            int maxVal = Integer.parseInt(maxTrainRiders.getText());
            int ridersCreated= simDriver.createRandomTrainRiders( minVal, maxVal );
            JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceText("trainRidersCreatedMsg"), ridersCreated));
        }
    }

    private void btnCloseRandomTrainRidersActionPerformed(java.awt.event.ActionEvent evt) {
        panelRandomTrainRiders.setVisible(false);
        minTrainRiders.setInputVerifier( null );
        maxTrainRiders.setInputVerifier( null );
    }

    private void menuRandomBusRidersActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelAddTrain.isVisible()) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible()) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomTrainRiders.isVisible() ) {
            panelRandomTrainRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelRandomBusRiders.isVisible()) {
            panelRandomBusRiders.setVisible(true);
        }
    }

    private void menuRandomTrainRidersActionPerformed(java.awt.event.ActionEvent evt) {
        if( panelAddBus.isVisible()) {
            panelAddBus.setVisible(false);
        }
        if( panelAddBusStop.isVisible()) {
            panelAddBusStop.setVisible(false);
        }
        if( panelAddBusRoute.isVisible()) {
            panelAddBusRoute.setVisible(false);
        }
        if( panelAddBusRider.isVisible() ) {
            panelAddBusRider.setVisible(false);
        }
        if( panelExtendBusRoute.isVisible() ) {
            panelExtendBusRoute.setVisible(false);
        }
        if( panelAddTrain.isVisible()) {
            panelAddTrain.setVisible(false);
        }
        if( panelAddTrainStop.isVisible() ) {
            panelAddTrainStop.setVisible(false);
        }
        if( panelAddTrainRider.isVisible() ) {
            panelAddTrainRider.setVisible(false);
        }
        if( panelExtendTrainRoute.isVisible()) {
            panelExtendTrainRoute.setVisible(false);
        }
        if( panelRandomBusRiders.isVisible()) {
            panelRandomBusRiders.setVisible(false);
        }
        if( panelAddBusTravelTimeFactor.isVisible() ) {
            panelAddBusTravelTimeFactor.setVisible(false);
        }
        if( !panelRandomTrainRiders.isVisible()) {
            panelRandomTrainRiders.setVisible(true);
            minTrainRiders.setInputVerifier( integerVerifier );
            maxTrainRiders.setInputVerifier( integerVerifier );
        }
    }

    private void menuMoveBusEventActionPerformed(java.awt.event.ActionEvent evt) {
        List<Integer> busList = simDriver.getBusIds();
        int noOfBuses = busList.size() > 0 ? busList.size() : 1;
        Integer[] busIds = new Integer[noOfBuses];

        int i = 0;
        for( Integer tmpBusId: busList ) {
            busIds[i++] = tmpBusId;
        }
        Integer busId = (Integer) JOptionPane.showInputDialog( null ,getResourceText("moveBusEventMessage"),
                getResourceText("moveBusEventDialogTitle"), JOptionPane.QUESTION_MESSAGE, null, busIds, EMPTY);
        if( null == busId ) {
            JOptionPane.showMessageDialog(null,getResourceText("moveBusEventBusIdNotSelectedError"));
        } else {
            simDriver.addEventToSimEngine(1,"move_bus", busId );
        }
    }

    private void menuMoveTrainEventActionPerformed(java.awt.event.ActionEvent evt) {
        List<Integer> trainList = simDriver.getTrainIds();
        int noOfTrains = trainList.size() > 0 ? trainList.size() : 1;
        Integer[] trainIds = new Integer[noOfTrains];
        int i = 0;
        for( Integer tmpTrainId: trainList ) {
            trainIds[i++] = tmpTrainId;
        }
        Integer trainId = (Integer) JOptionPane.showInputDialog( null ,getResourceText("moveTrainEventMessage"),
                getResourceText("moveTrainEventDialogTitle"), JOptionPane.QUESTION_MESSAGE, null, trainIds, EMPTY);
        if( null == trainId ) {
            JOptionPane.showMessageDialog(null,getResourceText("moveTrainEventTrainIdNotSelectedError"));
        } else {
            simDriver.addEventToSimEngine(1,"move_train", trainId);
        }
    }

    public void hideAllPanels() {
        panelAddBus.setVisible(false);
        panelAddBusStop.setVisible(false);
        panelAddBusRoute.setVisible(false);
        panelAddTrain.setVisible(false);
        panelAddTrainStop.setVisible(false);
        panelAddTrainRoute.setVisible(false);
        panelAddBusRider.setVisible(false);
        panelAddTrainRider.setVisible(false);
        panelExtendBusRoute.setVisible(false);
        panelExtendTrainRoute.setVisible(false);
        panelRandomBusRiders.setVisible(false);
        panelRandomTrainRiders.setVisible(false);
        panelAddBusTravelTimeFactor.setVisible(false);
    }

    public void openDisplayModels() {
        try {
            Process p1 = Runtime.getRuntime().exec(getResourceText("graphVizBusGraphCommand") );
            Process p2 = Runtime.getRuntime().exec(getResourceText("graphVizTrainGraphCommand") );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, getResourceText("unableToShowGraphError"));
        }
    }

    private void btnAddBusTtFactorActionPerformed(java.awt.event.ActionEvent evt) {
        Integer routeId = (Integer) busTtFactorRouteIdComboBox.getSelectedItem();
        Integer stopId = (Integer) busTtFactorStopIdComboBox.getSelectedItem();
        Double factor = Double.parseDouble( busTtFactor.getText() );
        simDriver.setBusTravelTimeFactor( routeId, factor, stopId );
        JOptionPane.showMessageDialog(null, "Travel time factor for bus stop: " + stopId +" on route: "+routeId+ " is changed to " + factor);
    }

    private void btnCloseBusTtFactorPanelActionPerformed(java.awt.event.ActionEvent evt) {
        panelAddBusTravelTimeFactor.setVisible(false);
        busTtFactor.setText(EMPTY);
    }

    private String getResourceText(String key) {
        return rb.getString(key);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MtsUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MtsUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MtsUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MtsUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MtsUi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel addBusDetailsPanelHeading;
    private javax.swing.JLabel addBusRouteHeading;
    private javax.swing.JLabel addBusStopHeading;
    private javax.swing.JLabel addTrainRouteHeading;
    private javax.swing.JLabel addTrainStopHeading;
    private javax.swing.JButton btnAddBus;
    private javax.swing.JButton btnAddBusRoute;
    private javax.swing.JButton btnAddBusStop;
    private javax.swing.JButton btnAddTrain;
    private javax.swing.JButton btnAddTrainRoute;
    private javax.swing.JButton btnAddTrainStop;
    private javax.swing.JButton btnCloseAddBusPanel;
    private javax.swing.JButton btnCloseAddBusRoutePanel;
    private javax.swing.JButton btnCloseAddBusStopPanel;
    private javax.swing.JButton btnCloseAddTrainPanel;
    private javax.swing.JButton btnCloseAddTrainRoutePanel;
    private javax.swing.JButton btnCloseAddTrainStopPanel;
    private javax.swing.JTextField busCapacity;
    private javax.swing.JTextField busId;
    private javax.swing.JTextField busLocation;
    private javax.swing.JComboBox<Integer> busRouteId;
    private javax.swing.JTextField busRouteName;
    private javax.swing.JTextField busRouteNumber;
    private javax.swing.JTextField busSpeed;
    private javax.swing.JTextField busStopLatitude;
    private javax.swing.JTextField busStopLongitude;
    private javax.swing.JFileChooser fileChooser;
    private InputVerifier integerVerifier;
    private InputVerifier doubleVerifier;
    private ResourceBundle rb;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblBusId;
    private javax.swing.JLabel lblBusRoute;
    private javax.swing.JLabel lblBusRouteId;
    private javax.swing.JLabel lblBusRouteNumber;
    private javax.swing.JLabel lblBusSpeed;
    private javax.swing.JLabel lblTrainSpeed;
    private javax.swing.JLabel lblBusStopId;
    private javax.swing.JLabel lblBusStopLatitude;
    private javax.swing.JLabel lblBusStopLongitude;
    private javax.swing.JLabel lblBusStopName;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JLabel lblLocation1;
    private javax.swing.JLabel lblRouteName;
    private javax.swing.JLabel lblTotalCapacity;
    private javax.swing.JLabel lblTotalCapacity1;
    private javax.swing.JLabel lblTrainId;
    private javax.swing.JLabel lblTrainRoute;
    private javax.swing.JLabel lblTrainRouteId;
    private javax.swing.JLabel lblTrainRouteName;
    private javax.swing.JLabel lblTrainRouteNumber;
    private javax.swing.JLabel lblTrainStopId;
    private javax.swing.JLabel lblTrainStopLatitude;
    private javax.swing.JLabel lblTrainStopLongitude;
    private javax.swing.JLabel lblTrainStopName;
    private javax.swing.JMenuItem menuAddBus;
    private javax.swing.JMenuItem menuAddBusRoute;
    private javax.swing.JMenuItem menuAddBusStop;
    private javax.swing.JMenuItem menuAddTrain;
    private javax.swing.JMenuItem menuAddTrainRoute;
    private javax.swing.JMenuItem menuAddTrainStop;
    private javax.swing.JMenu menuBusSystem;
    private javax.swing.JMenu menuUploadCommands;
    private javax.swing.JMenuItem menuFileCommands;
    private javax.swing.JMenuItem menuUploadDataFromDb;
    private javax.swing.JMenu menuTrainSystem;
    private javax.swing.JMenuBar mtsMenuBar;
    private javax.swing.JTextField nameOfBusStop;
    private javax.swing.JPanel panelAddBus;
    private javax.swing.JPanel panelAddBusRoute;
    private javax.swing.JPanel panelAddBusStop;
    private javax.swing.JPanel panelAddTrain;
    private javax.swing.JPanel panelAddTrainRoute;
    private javax.swing.JPanel panelAddTrainStop;
    private javax.swing.JMenu menuSimConditions;
    private javax.swing.JTextField trainCapacity;
    private javax.swing.JTextField trainInputLocation;
    private javax.swing.JComboBox<Integer> trainRouteId;
    private javax.swing.JTextField trainRouteName;
    private javax.swing.JTextField trainRouteNumber;
    private javax.swing.JTextField trainSpeed;
    private javax.swing.JTextField trainStopLatitude;
    private javax.swing.JTextField trainStopLongitude;
    private javax.swing.JTextField trainStopName;
    private javax.swing.JTextField uniqueBusRouteId;
    private javax.swing.JTextField uniqueBusStopId;
    private javax.swing.JTextField uniqueTrainId;
    private javax.swing.JTextField uniqueTrainRouteId;
    private javax.swing.JTextField uniqueTrainStopId;
    private javax.swing.JMenu menuReports;
    private javax.swing.JMenuItem menuViewBusModel;
    private javax.swing.JMenuItem menuViewTrainModel;
    private javax.swing.JMenuItem menuViewBuses;
    private javax.swing.JMenuItem menuViewTrains;
    private javax.swing.JMenuItem menuViewBusRoutes;
    private javax.swing.JMenuItem menuViewTrainRoutes;
    private javax.swing.JMenuItem menuViewBusStops;
    private javax.swing.JMenuItem menuViewTrainStops;

    //Add Bus Rider Panel
    private javax.swing.JPanel panelAddBusRider;
    private javax.swing.JLabel lblPanelAddBusRiderHeading;
    private javax.swing.JLabel lblComboSrcBusStopId;
    private javax.swing.JComboBox<Integer> srcBusStopIdComboBox;
    private javax.swing.JLabel lblComboDestBusStopId;
    private javax.swing.JComboBox<Integer> destBusStopIdComboBox;
    private javax.swing.JLabel lblComboBusRouteId;
    private javax.swing.JComboBox<Integer> busRouteIdComboBox;
    private javax.swing.JLabel lblNoOfBusRiders;
    private javax.swing.JTextField noOfBusRiders;
    private javax.swing.JButton btnAddBusRiders;
    private javax.swing.JButton btnCloseAddBusRiders;

    //Menu - Add Bus Riders
    private javax.swing.JMenuItem menuAddBusRiders;

    private javax.swing.JPanel panelAddTrainRider;
    private javax.swing.JLabel lblPanelAddTrainRiderHeading;
    private javax.swing.JLabel lblComboSrcTrainStopId;
    private javax.swing.JComboBox<Integer> srcTrainStopIdComboBox;
    private javax.swing.JLabel lblComboDestTrainStopId;
    private javax.swing.JComboBox<Integer> destTrainStopIdComboBox;
    private javax.swing.JLabel lblComboTrainRouteId;
    private javax.swing.JComboBox<Integer> trainRouteIdComboBox;
    private javax.swing.JLabel lblNoOfTrainRiders;
    private javax.swing.JTextField noOfTrainRiders;
    private javax.swing.JButton btnAddTrainRiders;
    private javax.swing.JButton btnCloseAddTrainRiders;

    //Menu - Add Train Riders
    private javax.swing.JMenuItem menuAddTrainRiders;

    //Extend Bus and Train Route
    private javax.swing.JMenuItem menuExtendBusRoute;
    private javax.swing.JMenuItem menuExtendTrainRoute;
    private javax.swing.JPanel panelExtendBusRoute;
    private javax.swing.JPanel panelExtendTrainRoute;
    private javax.swing.JLabel lblPanelExtendBusRouteHeading;
    private javax.swing.JLabel lblPanelExtendTrainRouteHeading;
    private javax.swing.JLabel lblBusAvgTravelTime;
    private javax.swing.JLabel lblTrainAvgTravelTime;
    private javax.swing.JLabel lblComboExtendBusRouteId;
    private javax.swing.JLabel lblComboExtendTrainRouteId;
    private javax.swing.JLabel lblComboExtendBusRouteBusStopId;
    private javax.swing.JLabel lblComboExtendTrainRouteTrainStopId;
    private javax.swing.JButton btnExtendBusRoute;
    private javax.swing.JButton btnCloseExtendBusRoute;
    private javax.swing.JButton btnExtendTrainRoute;
    private javax.swing.JButton btnCloseExtendTrainRoute;
    private javax.swing.JComboBox<Integer> extendBusRouteBusStopComboBox;
    private javax.swing.JComboBox<Integer> extendTrainRouteTrainStopComboBox;
    private javax.swing.JComboBox<Integer> extendBusRouteBusRouteIdComboBox;
    private javax.swing.JComboBox<Integer> extendTrainRouteTrainRouteIdComboBox;
    private javax.swing.JTextField avgBusTravelTime;
    private javax.swing.JTextField avgTrainTravelTime;

    private javax.swing.JPanel panelRandomBusRiders;
    private javax.swing.JLabel lblPanelRandomBusRidersHeading;
    private javax.swing.JLabel lblMaxBusRiders;
    private javax.swing.JLabel lblMinBusRiders;
    private javax.swing.JButton btnAddRandomBusRiders;
    private javax.swing.JButton btnCloseRandomBusRiders;
    private javax.swing.JTextField minBusRiders;
    private javax.swing.JTextField maxBusRiders;
    private javax.swing.JMenuItem menuRandomBusRiders;

    private javax.swing.JPanel panelRandomTrainRiders;
    private javax.swing.JLabel lblPanelRandomTrainRidersHeading;
    private javax.swing.JLabel lblMaxTrainRiders;
    private javax.swing.JLabel lblMinTrainRiders;
    private javax.swing.JButton btnAddRandomTrainRiders;
    private javax.swing.JButton btnCloseRandomTrainRiders;
    private javax.swing.JTextField minTrainRiders;
    private javax.swing.JTextField maxTrainRiders;
    private javax.swing.JMenuItem menuRandomTrainRiders;

    private javax.swing.JMenu menuEvent;
    private javax.swing.JMenuItem menuMoveBusEvent;
    private javax.swing.JMenuItem menuMoveTrainEvent;
    private javax.swing.JMenu menuStartSimulation;

    private javax.swing.JButton btnCloseBusTtFactorPanel;
    private javax.swing.JComboBox<Integer> busTtFactorRouteIdComboBox;
    private javax.swing.JComboBox<Integer> busTtFactorStopIdComboBox;
    private javax.swing.JLabel lblBusTtFactorRouteId;
    private javax.swing.JLabel lblBusTtFactorStopId;
    private javax.swing.JLabel lblPanelAddBusTravelTimeFactorHeading;
    private javax.swing.JButton btnAddBusTtFactor;
    private javax.swing.JPanel panelAddBusTravelTimeFactor;
    private javax.swing.JLabel lblBusFactor;
    private javax.swing.JTextField busTtFactor;
    private javax.swing.JMenuItem menuBusTtFactor;

    private javax.swing.JMenuItem menuSimFactors;
    private javax.swing.JLabel movingBusLabel;
    private javax.swing.JLabel teamName;

    private javax.swing.JScrollPane consoleLogs;
    private javax.swing.JTextArea consoleLogsTextArea;
    private javax.swing.JMenuItem menuStepOnce;
    private javax.swing.JMenuItem menuStepMulti;

    private javax.swing.JDialog consoleLogsDialog;
    private PrintStream psOut;
}