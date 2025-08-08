# MemoryCard: The Database App made by gamers, for gamers

**MemoryCard** is an Android app that is makes managing your game library across multiple platform ecosystems easier. Most game platforms like Playstation Plus, Xbox Live, Steam and Nintendo Online already have places to implement 


## Requirements for using the app:

 - **Internet Access**: To access images from the internet, the application requires internet access.
 - **Storage Access**: Because this app uses a CSV file for it's database, you may have to add the user_game_library.csv from the assets folder in the Android Studio project to this specific path in your Device Explorer: /data/user/0/edu.utsa.cs3443.memorycard2/files. We have found that this isn't necessary in regular testing, but we're adding this here just to be sure.

# Usage
### Adding a game
To add a game, visit the plus sign on the bottom navigation bar.
Here, you will be greeted with a few text entry boxes where you put in info about your game:

1. Game title
2. Game platform (Steam, Xbox, Playstation, Nintendo)
3. Game Genre
4. Release Year
5. Game Publisher
6. Year Completed (Current year if not completed yet)
7. A short game review
8. A rating from 1 - 5
9. A URL from the internet with an image of the cover art for the game. **NOTE**: For an image to show up accurately, you must use a valid URL that ends in an image format in either .jpg or .png, and the image must be the only thing on the page.  [Here is a good example](https://upload.wikimedia.org/wikipedia/en/f/fa/Half-Life_Cover_Art.jpg), and [Here is a bad example.](https://en.wikipedia.org/wiki/Half-Life_%28video_game%29#/media/File:Half-Life_Cover_Art.jpg)
10. A checkbox for if you have completed the game
11. A checkbox for if the game is a favorite of yours
When you have verified that you have entered all of the information correctly, press **Add Game**. 





### Viewing libraries
To view your current libraries of games, visit the ribbon sign on the bottom navigation bar. Here, you will be able to have an overview of the 3 different types of libraries for each game platform: All, Favorites, and Completed.

 1. Click on the platform that you wish to view the library for
 2. Click on the arrow for the specific library that you wish to view
 3. Click on a game's image to view information about it specifically.

### Viewing statistics
To view some generated statistics about your game, press the smiley face on the bottom navigation bar.

Here, you can find specific statistics about your overall game collection, including:

 - Recent Playtime
 - Lifetime Playtime
 - Favorite Console
 - Completion Rate

### Using the stopwatch
To track new playtime for a specific game, press the clock on the bottom navigation bar.

Here, you will see two dropdown menus. One for game platforms, and one for specific games.

To add playtime for a game, select the platform that the game belongs to, then press the game itself. You may have to scroll down for larger libraries.

 - To start automatically tracking time for a game, press **Start**
 - To stop tracking time for a game and automatically update it in your games data, press **Stop**
 - If you decide you are currently tracking time but don't want to add it to your game data, press **Reset**


## Known Issues

- **Reload**: Unfortunately, any new games or updated playtime you add when using the app only stays persistent on restart of the app while your phone (or emulator, in the case of Android Studio) is running. If you are using Android Studio to run the app, every time you restart the actual running of the code, your CSV file may not stay persistent in your emulator.
 - **Adding Playstation games**: Sometimes, adding a game with the console name being "PS1", "PS2" etc. is not
 - **Updating favorite count in Statistics**: Sometimes, the number of the console with most favorited games in the Statistics tab will not update unless more than 1 game with the status of being a favorite has been added since reload
 - **Loading time for Images**: Since images are being pulled from the internet via the user's input, it may take a few seconds for images to load on in the library screen.


## Credits
This app was made by:

 - **Maxwell Scalzo**
 - **Trent Cordova**
 - **Elias Gil**

as our final project for CS 3443: Application Programming.
