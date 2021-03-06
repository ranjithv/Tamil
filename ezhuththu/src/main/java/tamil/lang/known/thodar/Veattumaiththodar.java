package tamil.lang.known.thodar;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.idai.VUrubu;

/**
 * <p>
 *   Represents  வேற்றுமைத்தொடர்
 * </p>
 *
 * @author velsubra
 */
public class Veattumaiththodar extends AbstractThodarMozhi {
    public Veattumaiththodar(TamilWord word, IPeyarchchol p, VUrubu u) {
        super(word, p, u);
    }
//
//    @Override
//    public List<TYPE_SIG> getTypes() {
//        return null;
//    }

    public TamilWord getType() {

       TamilWord  word = (TamilWord.from(getUrubu().getNumber()+"ஆம் "));
        word.addAll(super.getType());
        return  word;
    }

    public VUrubu getUrubu() {
        return (VUrubu) getWords().get(getWords().size() - 1);
    }
}
