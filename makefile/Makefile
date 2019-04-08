# souce: http://www.puxan.com/web/howto-write-generic-makefiles/
# Declaration of variables
CC = g++
CC_FLAGS = -Wall -std=c++17
VALGRIND = valgrind

# File names
EXEC = run
SOURCES = $(wildcard *.cpp)
OBJECTS = $(SOURCES:.cpp=.o)

# Main target
$(EXEC): $(OBJECTS)
        $(CC) $(OBJECTS) -o $(EXEC)

# To obtain object files
%.o: %.cpp
        $(CC) -c $(CC_FLAGS) $< -o $@

analyze: $(EXEC)
        $(VALGRIND) ./$(EXEC)

# To remove generated files
clean:
        rm -f $(EXEC) $(OBJECTS)
