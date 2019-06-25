# souce: http://www.puxan.com/web/howto-write-generic-makefiles/
# Declaration of variables
CC = g++
CC_FLAGS = -std=c++17 -Wall -Wextra -Werror -pedantic -pedantic-errors
LINKER_FLAGS = 
VALGRIND = valgrind

# File names
EXEC = exec
SOURCES = $(wildcard *.cpp)
OBJECTS = $(SOURCES:.cpp=.o)

# Main target
$(EXEC): $(OBJECTS)
	$(CC) $(OBJECTS) $(LINKER_FLAGS) -o $(EXEC)

# To obtain object files
%.o: %.cpp
	$(CC) -c $(CC_FLAGS) $< -o $@

analyze: $(EXEC)
	$(VALGRIND) ./$(EXEC)

# To remove generated files
clean:
	rm -f $(EXEC) $(OBJECTS)
