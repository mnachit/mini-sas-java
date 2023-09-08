# Define the Java compiler and flags
JAVAC = javac
JFLAGS = -cp .:./src:path/to/your/dependencies.jar

# Define the main class name (replace 'mohamed.ConsoleLogin' with your actual main class)
MAIN_CLASS = mohamed.ConsoleLogin

# Define the source and build directories
SRC_DIR = src
BUILD_DIR = build

# Define the source files and build targets
SRCS = $(wildcard $(SRC_DIR)/*.java)
OBJS = $(SRCS:$(SRC_DIR)/%.java=$(BUILD_DIR)/%.class)

# Default target: compile and run the main class
all: $(OBJS)
	java $(JFLAGS) $(MAIN_CLASS)

# Compile Java source files into class files
$(BUILD_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(@D)
	$(JAVAC) $(JFLAGS) -d $(BUILD_DIR) $<

# Clean the build directory
clean:
	rm -rf $(BUILD_DIR)

.PHONY: all clean

