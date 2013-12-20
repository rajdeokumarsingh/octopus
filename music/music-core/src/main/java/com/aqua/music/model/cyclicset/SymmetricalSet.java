package com.aqua.music.model.cyclicset;

import static com.aqua.music.model.core.BaseNote.*;

import java.util.ArrayList;
import java.util.List;

import open.music.api.PlayApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqua.music.model.core.BaseNote;
import com.aqua.music.model.core.ClassicalNote;
import com.aqua.music.model.core.Frequency;
import com.aqua.music.model.core.FrequencySet;

/**
 * 
 * This set uses same set of notes in ascend and descend
 * 
 * @author shruti.tiwari
 * 
 */
public enum SymmetricalSet implements FrequencySet {
	THAAT_ASAVARI(S, R, G_, M, P, D_, N_),
	THAAT_BHAIRAV(S, R_, G, M, P, D_, N),
	THAAT_BHAIRAVI(S, R_, G_, M, P, D_, N_),
	THAAT_BILAWAL(S, R, G, M, P, D, N),
	THAAT_KAFI(S, R, G_, M, P, D, N_),
	THAAT_KALYAN(S, R, G, M_, P, D, N),
	THAAT_KHAMAJ(S, R, G, M, P, D, N_),
	THAAT_MARWA(S, R_, G, M_, P, D, N),
	THAAT_PURVI(S, R_, G, M_, P, D_, N),
	THAAT_TODI(S, R_, G_, M_, P, D_, N);

	private final BaseNote[] baseAscendNotes;
	private final Frequency[] mainAscendNotes;
	private final Frequency[] mainDescendNotes;
	private final Logger logger = LoggerFactory.getLogger(SymmetricalSet.class);

	private SymmetricalSet(BaseNote... ascendNotes) {
		this.baseAscendNotes = ascendNotes;
		this.mainAscendNotes = new Frequency[baseAscendNotes.length + 1];
		int i = 0;
		for (BaseNote each : baseAscendNotes) {
			mainAscendNotes[i++] = each.getFrequencyObject(Octave.MAIN_OCTAVE);
		}
		mainAscendNotes[i] = baseAscendNotes[0].getFrequencyObject(Octave.UPPER_OCTAVE);
		this.mainDescendNotes = Util.reverse(mainAscendNotes);
	}

	public Frequency[] ascendNotes() {
		return mainAscendNotes;
	}

	public Frequency[] descendNotes() {
		return mainDescendNotes;
	}

	public Frequency[][] ascendDescendNotes(ClassicalNote startClassicalNote, ClassicalNote endClassicalNote) {
		List<Frequency> resultAscendNoteList = new ArrayList<Frequency>();
		Octave octave = startClassicalNote.octave();
		boolean startNoteFound = false;
		boolean endNoteFound = false;

		do {
			for (BaseNote each : baseAscendNotes) {
				if (each != startClassicalNote.baseNote()) {
					if (!startNoteFound) {
						continue;
					}
				} else {
					startNoteFound = true;
				}
				Frequency currentNote = each.getFrequencyObject(octave);
				resultAscendNoteList.add(currentNote);
				if (currentNote.frequencyInHz() == endClassicalNote.frequencyInHz()) {
					endNoteFound=true;
					break;
				}
			}
			octave = octave.next();
		}while (!endNoteFound);
		logger.info("generating pattern for resultAscendNoteList[" + resultAscendNoteList + "]");
		Frequency[] resultAscendNotes = resultAscendNoteList.toArray(new Frequency[resultAscendNoteList.size()]);
		return new Frequency[][] { resultAscendNotes, Util.reverse(resultAscendNotes) };
	}

	public String type() {
		return "THAAT";
	}
}