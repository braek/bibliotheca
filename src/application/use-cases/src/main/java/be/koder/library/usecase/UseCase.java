package be.koder.library.usecase;

import be.koder.library.domain.command.Command;

public interface UseCase<COMMAND extends Command, PRESENTER> {
    void execute(COMMAND command, PRESENTER presenter);
}