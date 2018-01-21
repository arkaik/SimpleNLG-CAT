package simplenlg.morphophonology;

import java.util.List;

import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGModule;
import simplenlg.framework.StringElement;

public abstract class MorphophonologyProcessor extends NLGModule {
	
	protected MorphophonologyRules morphophonologyRules;
	
	public MorphophonologyProcessor(MorphophonologyRules morphophonologyRules) {
        this.morphophonologyRules = morphophonologyRules;
    }
	
	@Override
	public void initialise() {
	}
	
	public NLGElement realise(NLGElement curElement, NLGElement nextElement) {
		
		if (curElement instanceof StringElement) {
			StringElement leftWord = (StringElement) curElement;
			if (nextElement instanceof StringElement)
			{
				StringElement rightWord = (StringElement) nextElement;
				MorphophonologyRules leftWordRules = morphophonologyRules;
				leftWordRules.doMorphophonology(leftWord, rightWord);
			}
		}
		else {
			List<NLGElement> childrenList = curElement.getChildren();
			int nbElements = childrenList.size();
			if (nbElements > 0) {
				NLGElement current = childrenList.get(0), next;
				realise(current);
				for (int index = 1; index < nbElements; index++) {
					next = childrenList.get(index);
					realise(next);
					StringElement rightCurrent = current.getRightMostStringElement();
					if (rightCurrent != null) {
						realise(rightCurrent, next.getLeftMostStringElement());
					}
					current = next;
				}
			}
		}
		
		return curElement;
	}

	@Override
	public NLGElement realise(NLGElement element) {
		return realise(element, null);
	}

	@Override
	public List<NLGElement> realise(List<NLGElement> elements) {
		
		return null;
	}

}
