/**
 * @author Carter Hill
 * 11162143
 * cgh418
 * Assignment 3
 * Question 4
 */

package assignment3;

import lib280.exception.ContainerEmpty280Exception;
import lib280.list.LinkedList280;
import lib280.tree.BasicMAryTree280;

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
	 * @return : LinkedList280<Skill> - List of the skills needed to learn
	 */
	public LinkedList280<Skill> skillDependencies(String name){
		
		LinkedList280<Skill> list = new LinkedList280<Skill>();
		list = this.findName(name, this, list);
		if(list == null)
			throw new RuntimeException("Name not in thingy!");
		//System.out.println(list.toString());
		return list;

	}
	
	/**
	 * Helper function for skillDependencies. Finds the Skill of the input name,
	 * and inserts it in a list, and then adds findName of the parent to the list
	 * 
	 * @param name - A string of the skill name we are searching for
	 * @param t - The SkillTree in which we are searching for the Skill
	 * @param L - The list we are adding the skills to
	 * @return : LinkedList280<Skill> - A list of a skill and it's dependencies
	 */
	private LinkedList280<Skill> findName(String name, SkillTree t, LinkedList280<Skill> L){
		if(t.rootItem().getSkillName() == name){
			L.insert(t.rootItem());
			return L;
		} else if (this.rootLastNonEmptyChild() == 0){
			return null;
		} else {
			for(int i = 1; i < this.rootLastNonEmptyChild()+1;i++){
				if(t.rootSubTree(i).findName(name,t.rootSubTree(i),L ) != null){
					//L.insert(t.rootSubTree(i).findName(name,t.rootSubTree(i),L ).firstItem());
					return t.findName(t.rootItem().getSkillName(), t, L);
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns the sum cost of all skills required to learn the input skill
	 * @param name - The name of the skill to be learned
	 * @return : int - All the skill costs added together
	 */
	public int skillTotalCost(String name){
		
		LinkedList280<Skill> list = new LinkedList280<Skill>();
		list = this.findName(name, this, list);
		if(list == null)
			throw new RuntimeException("Name not in thingy!");
		
		int cost = 0;
		list.goFirst();
		while(!list.after()){
			cost = cost + list.item().getSkillCost();
			list.goForth();
		}
		
		return cost;
		
	}
	
	public static void main(String[] args) {
		
		Boolean success = true;
		
		// Create the Skill classes
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
		
		// Create each tree
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
		
		// Set the subtree
		Crawl.setRootSubtree(Walk, 1);
		Crawl.setRootSubtree(Slap, 2); 
		Walk.setRootSubtree(Run, 1);
		Run.setRootSubtree(Kick, 1);
		Kick.setRootSubtree(Spinkick, 1);
		Kick.setRootSubtree(Flykick, 2);
		Slap.setRootSubtree(Punch, 1);
		Punch.setRootSubtree(Block, 1);
		Block.setRootSubtree(FalconPunch, 1);
		Block.setRootSubtree(Counter, 2);
		
		System.out.println("My SkillTree:\n"+Crawl.toStringByLevel());
		
		LinkedList280<Skill> F = Crawl.skillDependencies("Falcon PUNCH");
		F.goFirst();
		System.out.println("\n==================================\n\nFalcon PUNCH dependencies:\n"+F.toString());
		System.out.println("\n==================================\n\nSpin Kick with Kick as root:");
		
		LinkedList280<Skill> K = Kick.skillDependencies("Spin Kick");
		K.goFirst();
		System.out.println(K.toString());
		System.out.println("\n==================================\n\nCounter dependencies:");
		
		LinkedList280<Skill> C = Crawl.skillDependencies("Counter");
		C.goFirst();
		System.out.println(C.toString());
		System.out.println("\n==================================\n");
		
		// Catch exception on getting a List with non-existing skill name
		Exception x = null;
		try {
			Crawl.skillDependencies("banana");
		} catch (RuntimeException e) {
			x = e;
		} finally {
			if (x == null){
				System.out.println("Expected exception searching for invalid Skill.  Got none.");
				success = false;
			}
		}
		
		// Catch exception on getting skill cost with non-existing skill name
		x = null;
		try {
			Crawl.skillTotalCost("banana");
		} catch (RuntimeException e) {
			x = e;
		} finally {
			if (x == null){
				System.out.println("Expected exception searching for invalid Skill.  Got none.");
				success = false;
			}
		}
		
		int cost = Crawl.skillTotalCost("Falcon PUNCH");
		System.out.println("Falcon PUNCH skill cost should be 19 and it is: "+cost);
		if(cost!=19)
			success=false;
		
		cost = Crawl.skillTotalCost("Crawl");
		System.out.println("Crawl total skill cost should be 1 and it is: "+cost);
		if(cost!=1)
			success=false;
		
		cost = Crawl.skillTotalCost("Kick");
		System.out.println("Kick total skill cost should be 8 and it is: "+cost);
		if(cost!=8)
			success=false;
		
		if(success){
			System.out.println("\nTesting succeeded! Great success!");
		} else {
			System.out.println("\nTesting failed D: ABORT MISSION!");
		}
		
	}

}
