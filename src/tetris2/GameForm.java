package tetris2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class GameForm extends javax.swing.JFrame
{
    private GameBoard gb;
    private GameThread gt;
    public GameForm()
    {
        initComponents();
        
        gb = new GameBoard( GamePlaceholder );
        this.add( gb );
        
        initControls();
    }
    
    public void startGame()
    // creates an array of type Color[][], colors the difficulty label, starts the game loop
    {
        gb.initBackground();
        initDifficultyLabel();
        gt = new GameThread( gb, this );
        gt.start();
    }
    
    private void initControls()
    // another blackbox
    {
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();
        
        im.put( KeyStroke.getKeyStroke( "RIGHT" ), "right" );
        im.put( KeyStroke.getKeyStroke( "LEFT" ), "left" );
        im.put( KeyStroke.getKeyStroke( "DOWN" ), "down" );
        im.put( KeyStroke.getKeyStroke( "UP" ), "up" );
        im.put( KeyStroke.getKeyStroke( 'p' ), "pause" );

        am.put( "right", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                gb.moveTetrominoRight();
                repaint();
            }
        } );
        am.put( "left", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                gb.moveTetrominoLeft();
                repaint();
            }
        } );
        am.put( "down", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                gb.dropTetromino();
                repaint();
            }
        } );
        am.put( "up", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                gb.rotateTetromino();
                repaint();
            }
        } );
        am.put( "pause", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                invertPause();
            }
        } );
    }
    
    private void initDifficultyLabel()
    {
        int d = Tetris2.getDifficulty();
        if( d == 0 )
        {
            difficultyLabel.setText( "Difficulty: Easy" );
            difficultyLabel.setForeground( Color.GREEN );
        }
        if( d == 1 )
        {
            difficultyLabel.setText( "Difficulty: Medium" );
            difficultyLabel.setForeground( Color.BLACK );
        }
        if( d == 2 )
        {
            difficultyLabel.setText( "Difficulty: HARD" );
            difficultyLabel.setForeground( Color.RED );
        }
    }
    
    private void invertPause()
    {
        this.gt.play = !this.gt.play;
        if( this.gt.play ) pauseButton.setText( "Pause" );
        else pauseButton.setText( "Play" );
    }
    
    public void updateScore( int score )
    {
        scoreLabel.setText( "Score: " + score );
    }
    
    public void updateLevel( int level )
    {
        levelLabel.setText( "Level: " + level );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        GamePlaceholder = new javax.swing.JPanel();
        scoreLabel = new javax.swing.JLabel();
        levelLabel = new javax.swing.JLabel();
        pauseButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        difficultyLabel = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(230, 230, 230));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setResizable(false);

        GamePlaceholder.setBackground(new java.awt.Color(250, 250, 250));
        GamePlaceholder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        GamePlaceholder.setPreferredSize(new java.awt.Dimension(200, 400));

        javax.swing.GroupLayout GamePlaceholderLayout = new javax.swing.GroupLayout(GamePlaceholder);
        GamePlaceholder.setLayout(GamePlaceholderLayout);
        GamePlaceholderLayout.setHorizontalGroup(
            GamePlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        GamePlaceholderLayout.setVerticalGroup(
            GamePlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );

        scoreLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        scoreLabel.setText("Score: 0");
        scoreLabel.setMaximumSize(new java.awt.Dimension(120, 30));
        scoreLabel.setMinimumSize(new java.awt.Dimension(70, 30));
        scoreLabel.setPreferredSize(new java.awt.Dimension(120, 30));

        levelLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        levelLabel.setText("Level: 1");
        levelLabel.setMaximumSize(new java.awt.Dimension(100, 30));
        levelLabel.setMinimumSize(new java.awt.Dimension(100, 30));
        levelLabel.setPreferredSize(new java.awt.Dimension(100, 30));

        pauseButton.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        pauseButton.setText("Pause");
        pauseButton.setFocusable(false);
        pauseButton.setPreferredSize(new java.awt.Dimension(70, 30));
        pauseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pauseButtonMouseClicked(evt);
            }
        });
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        menuButton.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        menuButton.setText("Main Menu");
        menuButton.setFocusable(false);
        menuButton.setPreferredSize(new java.awt.Dimension(83, 30));
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        difficultyLabel.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        difficultyLabel.setText("Difficulty");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(levelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(difficultyLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(GamePlaceholder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(68, 68, 68))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(levelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(difficultyLabel)
                .addGap(23, 23, 23)
                .addComponent(GamePlaceholder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        
        invertPause();
        
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void pauseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseButtonMouseClicked

        
    }//GEN-LAST:event_pauseButtonMouseClicked

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed

        gt.interrupt();
        this.setVisible( false );
        Tetris2.showMenu();
        
    }//GEN-LAST:event_menuButtonActionPerformed

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
            java.util.logging.Logger.getLogger(GameForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater( new Runnable() {
            public void run() {
                new GameForm().setVisible( true );
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel GamePlaceholder;
    private javax.swing.JLabel difficultyLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton pauseButton;
    private javax.swing.JLabel scoreLabel;
    // End of variables declaration//GEN-END:variables
}
