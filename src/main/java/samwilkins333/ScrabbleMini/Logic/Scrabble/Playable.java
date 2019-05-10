package main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

interface Playable {

	void makeMove();
	boolean isHuman();
	PlayerNum getPlayerNumber();
	Word getNewestWord();

}