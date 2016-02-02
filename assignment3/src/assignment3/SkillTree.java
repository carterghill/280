package assignment3;

import lib280.list.LinkedList280;
import lib280.tree.BasicMAryTree280;

/**
 * @author carte
 *
 */
public class SkillTree extends BasicMAryTree280<Skill> {

	/**	
	 * Create tree with the specified root node and 
	 * specified maximum arity of nodes.  
	 * @timing O(1) 
	 * @param x item to set as the root node
	 * @param m number of children allowed for future nodes 
	 */
	public SkillTree(Skill x, int m)
	{
		super(x,m);
	}

	/**
	 * A convenience method that avoids typecasts.
	 * Obtains a subtree of the root.
	 * 
	 * @param i Index of the desired subtree of the root.
	 * @return the i-th subtree of the root.
	 */
	public SkillTree rootSubTree(int i) {
		return (SkillTree)super.rootSubtree(i);
	}

	/**
	 * Obtain a list of all the skills required to 
	 * learn the input skill
	 * 
	 * @param name - Name of the skill
	 * @return LinkedList280 of the skills needed to learn
	 */
	public LinkedList280<Skill> skillDependencies(String name){
		
		LinkedList280<Skill> list = new LinkedList280<Skill>();
		Skill s = null;
		
		if(this.rootItem().getSkillName() == name){
			list.insert(this.rootItem());
			return list;
		} else {
			for(int i = 1; i < this.rootLastNonEmptyChild()+1; i++){
				//System.out.println(this.rootSubTree(i).rootItem().getSkillName());
				if(this.rootSubTree(i).rootItem().getSkillName() == name){
					
					list.insert(this.rootSubTree(i).rootItem());
					list.lastNode().setNextNode(this.skillDependencies(this.rootItem().getSkillName()).firstNode());
				} else {
					if(this.rootSubTree(i).skillDependencies(name) != null){
						
					}
				}
			}
		}
		
		//System.out.println(list.toString());
		
		return list;

	}
	
	public Boolean exists(String name, SkillTree t){
		if(t.rootItem().getSkillName() == name){
			return true;
		} else if (this.rootLastNonEmptyChild() == 0){
			return false;
		} else {
			for(int i = 1; i < this.rootLastNonEmptyChild()+1;i++){
				if(t.rootSubTree(i).exists(name,t.rootSubTree(i) ))
					return true;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		Skill crawl = new Skill("Crawl","Gain +1 distance from enemy", 1);
		Skill walk = new Skill("Walk","Gain +2 distance from enemy", 2);
		Skill run = new Skill("Run","Gain +5 distance from enemy", 3);
		Skill kick = new Skill("Kick","Attack: 2, Range: 5", 2);
		Skill spinkick = new Skill("Spin Kick","Attack: 10, Range: 3", 5);
		Skill flykick = new Skill("Flying Sidekick","Attack: 5, Range: 10", 5);
		Skill slap = new Skill("Slap","Attack: 1, Range: 2", 1);
		Skill punch = new Skill("Punch","Attack: 3, Range: 2", 2);
		Skill block = new Skill("Block","Recoils half damage to opponent, can't use twice in a row", 5);
		Skill falconpunch = new Skill("Falcon PUNCH","Attack: 15, Range: 2", 10);
		Skill counter = new Skill("Counter","65% chance to return damage to attacker", 10);
		
		SkillTree Crawl = new SkillTree(crawl,2);
		SkillTree Walk = new SkillTree(walk,1);
		SkillTree Run = new SkillTree(run,1);
		SkillTree Kick = new SkillTree(kick,2);
		SkillTree Spinkick = new SkillTree(spinkick,0);
		SkillTree Flykick = new SkillTree(flykick,0);
		SkillTree Slap = new SkillTree(slap,1);
		SkillTree Punch = new SkillTree(punch,1);
		SkillTree Block = new SkillTree(block,2);
		SkillTree FalconPunch = new SkillTree(falconpunch,0);
		SkillTree Counter = new SkillTree(counter,0);
		
		Crawl.setRootSubtree(Walk, 1);
		Crawl.setRootSubtree(Slap, 2); 
		Walk.setRootSubtree(Run, 1);
		Run.setRootSubtree(Kick, 1);
		Kick.setRootSubtree(Spinkick, 1);
		Kick.setRootSubtree(Flykick, 2);
		Slap.setRootSubtree(Punch, 1);
		Punch.setRootSubtree(Block, 1);
		Block.insertRoot(falconpunch, 1);
		Block.insertRoot(counter, 2);
		
		LinkedList280<Skill> L = Crawl.skillDependencies("Block");
		L.goFirst();
		//System.out.println(L.firstItem());
		L.goForth();
		//System.out.println(L.item());
		
	}

}