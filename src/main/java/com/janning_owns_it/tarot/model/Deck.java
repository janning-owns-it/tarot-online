package com.janning_owns_it.tarot.model;

import java.util.Map;

public class Deck {

    Map<Integer, String> cards = Map.ofEntries(
            // Major Arcana
            Map.entry(0, "The Fool - Major Arcana"),
            Map.entry(1, "The Magician - Major Arcana"),
            Map.entry(2, "The High Priestess - Major Arcana"),
            Map.entry(3, "The Empress - Major Arcana"),
            Map.entry(4, "The Emperor - Major Arcana"),
            Map.entry(5, "The Hierophant - Major Arcana"),
            Map.entry(6, "The Lovers - Major Arcana"),
            Map.entry(7, "The Chariot - Major Arcana"),
            Map.entry(8, "Strength - Major Arcana"),
            Map.entry(9, "The Hermit - Major Arcana"),
            Map.entry(10, "Wheel of Fortune - Major Arcana"),
            Map.entry(11, "Justice - Major Arcana"),
            Map.entry(12, "The Hanged Man - Major Arcana"),
            Map.entry(13, "Death - Major Arcana"),
            Map.entry(14, "Temperance - Major Arcana"),
            Map.entry(15, "The Devil - Major Arcana"),
            Map.entry(16, "The Tower - Major Arcana"),
            Map.entry(17, "The Star - Major Arcana"),
            Map.entry(18, "The Moon - Major Arcana"),
            Map.entry(19, "The Sun - Major Arcana"),
            Map.entry(20, "Judgement - Major Arcana"),
            Map.entry(21, "The World - Major Arcana"),

            // Minor Arcana - Cups
            Map.entry(22, "Ace of Cups - Minor Arcana"),
            Map.entry(23, "2 of Cups - Minor Arcana"),
            Map.entry(24, "3 of Cups - Minor Arcana"),
            Map.entry(25, "4 of Cups - Minor Arcana"),
            Map.entry(26, "5 of Cups - Minor Arcana"),
            Map.entry(27, "6 of Cups - Minor Arcana"),
            Map.entry(28, "7 of Cups - Minor Arcana"),
            Map.entry(29, "8 of Cups - Minor Arcana"),
            Map.entry(30, "9 of Cups - Minor Arcana"),
            Map.entry(31, "10 of Cups - Minor Arcana"),
            Map.entry(32, "Page of Cups - Minor Arcana"),
            Map.entry(33, "Knight of Cups - Minor Arcana"),
            Map.entry(34, "Queen of Cups - Minor Arcana"),
            Map.entry(35, "King of Cups - Minor Arcana"),

            // Minor Arcana - Wands
            Map.entry(36, "Ace of Wands - Minor Arcana"),
            Map.entry(37, "2 of Wands - Minor Arcana"),
            Map.entry(38, "3 of Wands - Minor Arcana"),
            Map.entry(39, "4 of Wands - Minor Arcana"),
            Map.entry(40, "5 of Wands - Minor Arcana"),
            Map.entry(41, "6 of Wands - Minor Arcana"),
            Map.entry(42, "7 of Wands - Minor Arcana"),
            Map.entry(43, "8 of Wands - Minor Arcana"),
            Map.entry(44, "9 of Wands - Minor Arcana"),
            Map.entry(45, "10 of Wands - Minor Arcana"),
            Map.entry(46, "Page of Wands - Minor Arcana"),
            Map.entry(47, "Knight of Wands - Minor Arcana"),
            Map.entry(48, "Queen of Wands - Minor Arcana"),
            Map.entry(49, "King of Wands - Minor Arcana"),

            // Minor Arcana - Swords
            Map.entry(50, "Ace of Swords - Minor Arcana"),
            Map.entry(51, "2 of Swords - Minor Arcana"),
            Map.entry(52, "3 of Swords - Minor Arcana"),
            Map.entry(53, "4 of Swords - Minor Arcana"),
            Map.entry(54, "5 of Swords - Minor Arcana"),
            Map.entry(55, "6 of Swords - Minor Arcana"),
            Map.entry(56, "7 of Swords - Minor Arcana"),
            Map.entry(57, "8 of Swords - Minor Arcana"),
            Map.entry(58, "9 of Swords - Minor Arcana"),
            Map.entry(59, "10 of Swords - Minor Arcana"),
            Map.entry(60, "Page of Swords - Minor Arcana"),
            Map.entry(61, "Knight of Swords - Minor Arcana"),
            Map.entry(62, "Queen of Swords - Minor Arcana"),
            Map.entry(63, "King of Swords - Minor Arcana"),

            // Minor Arcana - Pentacles
            Map.entry(64, "Ace of Pentacles - Minor Arcana"),
            Map.entry(65, "2 of Pentacles - Minor Arcana"),
            Map.entry(66, "3 of Pentacles - Minor Arcana"),
            Map.entry(67, "4 of Pentacles - Minor Arcana"),
            Map.entry(68, "5 of Pentacles - Minor Arcana"),
            Map.entry(69, "6 of Pentacles - Minor Arcana"),
            Map.entry(70, "7 of Pentacles - Minor Arcana"),
            Map.entry(71, "8 of Pentacles - Minor Arcana"),
            Map.entry(72, "9 of Pentacles - Minor Arcana"),
            Map.entry(73, "10 of Pentacles - Minor Arcana"),
            Map.entry(74, "Page of Pentacles - Minor Arcana"),
            Map.entry(75, "Knight of Pentacles - Minor Arcana"),
            Map.entry(76, "Queen of Pentacles - Minor Arcana"),
            Map.entry(77, "King of Pentacles - Minor Arcana")
    );

    public Map<Integer, String> getCards() {
        return cards;
    }

}
