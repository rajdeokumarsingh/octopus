package com.aqua.music.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import com.aqua.music.model.Frequency;
import com.aqua.music.model.Frequency.ClassicalNote;
import com.aqua.music.model.FrequencySet;
import com.aqua.music.model.FrequencySet.SymmetricalSet;

interface FrequencyListBuilder {
	boolean NO_COMMA = false;
	boolean WITH_COMMA = true;

	Collection<Frequency> finalFrequencySequence();

	String prettyPrintText();

	static class BuilderForMultipleSymmetricalSets implements FrequencyListBuilder {
		final Collection<File> collectedAudioFile = new ArrayList<File>();
		final Collection<Frequency> collectedFrequencies = new ArrayList<Frequency>();
		final StringBuffer printableAudios = new StringBuffer();

		BuilderForMultipleSymmetricalSets(SymmetricalSet[] multipleSets) {
			for (SymmetricalSet each : multipleSets) {
				processEach(each);
			}
		}

		public Collection<Frequency> finalFrequencySequence() {
			return collectedFrequencies;
		}

		public String prettyPrintText() {
			return printableAudios.toString();
		}

		private void processEach(SymmetricalSet set) {
			FrequencyList listMaker = BuilderForSymmetricalSet.generate(set);
			collectedFrequencies.addAll(listMaker.allNotes);
			printableAudios.append("\n" + listMaker.prettyPrintText);
		}
	}

	static class BuilderForSymmetricalSet implements FrequencyListBuilder {
		private FrequencyList frequencyList;

		BuilderForSymmetricalSet(FrequencySet frequencySet) {
			this.frequencyList = generate(frequencySet);
		}

		private static FrequencyList generate(FrequencySet frequencySet) {
			// enqueue ascend sequence
			FrequencyList frequencyList = new FrequencyList();
			frequencyList.add(ClassicalNote.SA, NO_COMMA);
			frequencyList.add(frequencySet.ascendNotes());
			frequencyList.add(ClassicalNote.HIGH_SA, WITH_COMMA);

			frequencyList.addText(PermutationApplicator.SEP);

			// enqueue descend sequence
			frequencyList.add(ClassicalNote.HIGH_SA, NO_COMMA);
			frequencyList.add(frequencySet.descendNotes());
			frequencyList.add(ClassicalNote.SA, WITH_COMMA);
			return frequencyList;
		}

		public Collection<Frequency> finalFrequencySequence() {
			return frequencyList.allNotes;
		}

		public String prettyPrintText() {
			return frequencyList.prettyPrintText.toString();
		}
	}

	static class FrequencyList {
		Collection<Frequency> allNotes = new ArrayList<Frequency>();

		StringBuffer prettyPrintText = new StringBuffer();

		void add(Frequency singleNote) {
			add(singleNote, true);
		}

		void add(Frequency singleNote, boolean appendComma) {
			allNotes.add(singleNote);
			prettyPrintText.append((appendComma ? ", " : "") + singleNote.prettyPrint());
		}

		void add(Frequency[] notes) {
			for (Frequency each : notes) {
				add(each);
			}
		}

		void addText(String value) {
			prettyPrintText.append(value);
		}
	}

	static class SimpleListBuilder implements FrequencyListBuilder {
		private FrequencyList frequencyList;

		SimpleListBuilder(Collection<Frequency> allNotes) {
			this.frequencyList = new FrequencyList();
			for (Frequency each : allNotes) {
				frequencyList.add(each);
			}
		}

		public Collection<Frequency> finalFrequencySequence() {
			return frequencyList.allNotes;
		}

		@Override
		public String prettyPrintText() {
			return frequencyList.prettyPrintText.toString();
		}
	}

	static class WithMiddleNotesAndStartEndNotes implements FrequencyListBuilder {
		private FrequencyList frequencyList;

		WithMiddleNotesAndStartEndNotes(Frequency[] middleNotes, Frequency start, Frequency end) {
			this.frequencyList = new FrequencyList();
			frequencyList.add(start);
			frequencyList.add(middleNotes);
			frequencyList.add(end);
		}

		public Collection<Frequency> finalFrequencySequence() {
			return frequencyList.allNotes;
		}

		@Override
		public String prettyPrintText() {
			return frequencyList.prettyPrintText.toString();
		}
	}
}