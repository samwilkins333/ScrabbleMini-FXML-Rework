package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

/**
 * Enumerates the possible outcomes for playing a tile.
 * SUCCESS corresponds to a flash with a green check,
 * indicating all the word's attributes are valid.
 * QUALIFIED_FAILURE indicates either a syntactically valid word that is
 * out of position or otherwise breaks locational rules, or, in
 * the case of a placed word and its crosses, the word or cross might be
 * otherwise successful if another failed word or cross were not preventing
 * the entire move from being valid.
 * FAILURE indicates a syntactically invalid word.
 */
public enum OverlayType {
  SUCCESS,
  QUALIFIED_FAILURE,
  FAILURE
}
