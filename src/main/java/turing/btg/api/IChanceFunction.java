package turing.btg.api;

@FunctionalInterface
public interface IChanceFunction {
	float apply(float baseChance, int boost, int tier);
}
