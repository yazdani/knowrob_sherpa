##############################################################################
# CMake
##############################################################################

cmake_minimum_required(VERSION 2.8.3)
project(knowrob_sherpa)

##############################################################################
# Catkin
##############################################################################

find_package(catkin REQUIRED COMPONENTS rosjava_build_tools tf_prolog rosprolog cmd_mission rosjava_messages rosjava_bootstrap quadrotor_controller img_mission)

# Set the gradle targets you want catkin's make to run by default, e.g.
#   catkin_rosjava_setup(installApp)
# Note that the catkin_create_rosjava_xxx scripts will usually automatically
# add tasks to this for you when you create subprojects.
catkin_rosjava_setup(installApp publishMavenJavaPublicationToMavenRepository writeClasspath)

catkin_package(CATKIN_DEPENDS tf_prolog cmd_mission quadrotor_controller img_mission)

##############################################################################
# Installation
##############################################################################
install(DIRECTORY prolog/
  DESTINATION ${CATKIN_PACKAGE_SHARE_DESTINATION}/prolog)

# Change this to match the maven group name you have specified in the 
# allprojects closure the root build.gradle
install(DIRECTORY ${CATKIN_DEVEL_PREFIX}/${CATKIN_GLOBAL_MAVEN_DESTINATION}/com/github/${PROJECT_NAME}/ 
        DESTINATION ${CATKIN_GLOBAL_MAVEN_DESTINATION}/com/github/${PROJECT_NAME})
