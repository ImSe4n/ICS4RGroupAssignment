import java.util.*;
import java.io.*;
public class humanPlayer extends player
{
    //create private varaibles to store userName and password
    private String userName;
    private String userPassword;

    //create a int variable to store the player's highest score
    int highestScore;
    
    //code a deflaut constructor for humanPlayer
    
    humanPlayer(){
        this.userName = "unknown";
        this.userPassword = "unknown";
        this.highestScore = 0;
    }
    
    //code a constructor for humanPlayer
    
    humanPlayer(String name, String password, int score){
        this.userName = name;
        this.userPassword = password;
        this.highestScore = score;
    }
    
    //code getters and setters for private variables
    
    public void setUserName(String name){
        this.userName = name;
    }
    
    public void setUserPassword(String password){
        this.userPassword = password;
    }
    
    public String getUserName(){
        return this.userName;
    }
    
    public String getUserPassword(){
        return this.userPassword;
    }
    
    
    //create a method to store a player information into a file
    public void storePlayer(humanPlayer player){
        
        try {
            //use their user name as the file name
            PrintWriter output = new PrintWriter(new FileWriter(player.userName + ".txt"));
    
            output.println(player.getUserName());
            output.println(player.getUserPassword());
            output.println(player.highestScore);
    
            output.close();
        } catch (IOException e) {
            System.out.println("Error saving player information.");
        }
    }
    
    //create a method to upload information from the file by username and return the object
    public humanPlayer uploadFromFile(String username, String password){
        //create a humanPlayer variable to store information
        humanPlayer player = new humanPlayer();

        File file = new File(username + ".txt");
        
        //check if file exists
        if(file.exists()){

            try{
                BufferedReader input = new BufferedReader(new FileReader(file));
    
                String userName = input.readLine();
                String userPassword = input.readLine();
                int highestScore = Integer.parseInt(input.readLine());
                
                input.close();
                
                //check if the password matches
                if(this.userPassword == password){
                    System.out.println("Login successfully");
                    player = new humanPlayer(userName, userPassword, highestScore);
                    return player;
                }
                else{
                    System.out.println("User does not exist. Please enter a right username or password.");
                }
            
    
            }catch(IOException e){
                System.out.println("User does not exist. Please enter a right username or password.");
            }
    
        }else{
            System.out.println("User does not exist. Please enter a right username or password.");
        }
    }
}