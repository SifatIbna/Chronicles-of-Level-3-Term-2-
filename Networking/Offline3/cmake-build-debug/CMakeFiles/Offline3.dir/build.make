# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.17

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Disable VCS-based implicit rules.
% : %,v


# Disable VCS-based implicit rules.
% : RCS/%


# Disable VCS-based implicit rules.
% : RCS/%,v


# Disable VCS-based implicit rules.
% : SCCS/s.%


# Disable VCS-based implicit rules.
% : s.%


.SUFFIXES: .hpux_make_needs_suffix_list


# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = S:\Users\sifat\AppData\Local\JetBrains\Toolbox\apps\CLion\ch-0\202.7660.37\bin\cmake\win\bin\cmake.exe

# The command to remove a file.
RM = S:\Users\sifat\AppData\Local\JetBrains\Toolbox\apps\CLion\ch-0\202.7660.37\bin\cmake\win\bin\cmake.exe -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = T:\WorkSpace\Level_3_Term_2\Networking\Offline3

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = T:\WorkSpace\Level_3_Term_2\Networking\Offline3\cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Offline3.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Offline3.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Offline3.dir/flags.make

CMakeFiles/Offline3.dir/main.c.obj: CMakeFiles/Offline3.dir/flags.make
CMakeFiles/Offline3.dir/main.c.obj: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=T:\WorkSpace\Level_3_Term_2\Networking\Offline3\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/Offline3.dir/main.c.obj"
	T:\MinGW\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\Offline3.dir\main.c.obj   -c T:\WorkSpace\Level_3_Term_2\Networking\Offline3\main.c

CMakeFiles/Offline3.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Offline3.dir/main.c.i"
	T:\MinGW\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E T:\WorkSpace\Level_3_Term_2\Networking\Offline3\main.c > CMakeFiles\Offline3.dir\main.c.i

CMakeFiles/Offline3.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Offline3.dir/main.c.s"
	T:\MinGW\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S T:\WorkSpace\Level_3_Term_2\Networking\Offline3\main.c -o CMakeFiles\Offline3.dir\main.c.s

# Object files for target Offline3
Offline3_OBJECTS = \
"CMakeFiles/Offline3.dir/main.c.obj"

# External object files for target Offline3
Offline3_EXTERNAL_OBJECTS =

Offline3.exe: CMakeFiles/Offline3.dir/main.c.obj
Offline3.exe: CMakeFiles/Offline3.dir/build.make
Offline3.exe: CMakeFiles/Offline3.dir/linklibs.rsp
Offline3.exe: CMakeFiles/Offline3.dir/objects1.rsp
Offline3.exe: CMakeFiles/Offline3.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=T:\WorkSpace\Level_3_Term_2\Networking\Offline3\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable Offline3.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\Offline3.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Offline3.dir/build: Offline3.exe

.PHONY : CMakeFiles/Offline3.dir/build

CMakeFiles/Offline3.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\Offline3.dir\cmake_clean.cmake
.PHONY : CMakeFiles/Offline3.dir/clean

CMakeFiles/Offline3.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" T:\WorkSpace\Level_3_Term_2\Networking\Offline3 T:\WorkSpace\Level_3_Term_2\Networking\Offline3 T:\WorkSpace\Level_3_Term_2\Networking\Offline3\cmake-build-debug T:\WorkSpace\Level_3_Term_2\Networking\Offline3\cmake-build-debug T:\WorkSpace\Level_3_Term_2\Networking\Offline3\cmake-build-debug\CMakeFiles\Offline3.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Offline3.dir/depend

