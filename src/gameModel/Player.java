package gameModel;

import gameModel.Grid;

public interface Player {
	Direction selectDirection(Grid grid);
}
