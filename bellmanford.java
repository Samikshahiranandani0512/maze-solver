import java.util.Scanner;

public class new_bellman_ford

{
  private int distances[];
  private int predecessor[];
  private int number_of_vertices;
  public static final int MAX_VALUE = 9999;

  int[] input() {
    Scanner in = new Scanner(System.in);
    System.out.println("Enter the side length of the square matrix of your desired maze");
    int size = in.nextInt();

    int vertex_number_counter = 1;
    int vertex_x_coordinate[] = new int[(size + 1) * (size + 1)];
    int vertex_y_coordinate[] = new int[(size + 1) * (size + 1)];

    System.out.println("Enter 'X' for a blocked square in the maze.");
    System.out.println("Enter 'O' for a free path in the maze.");
    System.out.println("The point will be mentioned by the row number followed by the column number.");
    char maze_matrix[][] = new char[size + 1][size + 1];
    for (int i = 1; i < size + 1; i++) // inputs the maze as given by user
    {
      for (int j = 1; j < size + 1; j++) {
        System.out.println("Enter the specification ( X OR O ) for point " + i + ", " + j);
        maze_matrix[i][j] = in.next().charAt(0);
        if (maze_matrix[i][j] == 'O') {
          vertex_x_coordinate[vertex_number_counter] = j;
          vertex_y_coordinate[vertex_number_counter] = i;
          vertex_number_counter++;
        }
      }
    }
    System.out.println("The maze as per your specifications: ");
    for (int i = 1; i < size + 1; i++)// inputs the maze as given by user
    {
      for (int j = 1; j < size + 1; j++) {
        System.out.print(maze_matrix[i][j] + " ");
      }
      System.out.println();
    }
    // System.out.println("The vertices along with their x and y coordinates,
    // respectively:");
    for (int i = 1; i < vertex_number_counter; i++) {
      // System.out.println("Vertex No. " + i + ": " + vertex_x_coordinate[i]+ ", " +
      // vertex_y_coordinate[i]);
    }
    int number_of_vertices = vertex_number_counter - 1;

    int adjacency_matrix[][] = new int[number_of_vertices + 1][number_of_vertices + 1];

    for (int i = 0; i < number_of_vertices + 1; i++) {
      for (int j = 0; j < number_of_vertices + 1; j++)

      {
        if (i == j)
          adjacency_matrix[i][j] = 0;

        else
          adjacency_matrix[i][j] = MAX_VALUE;
      }
    }

    int x_to_be_located = 0;
    int current_x_coordinate = 0;
    int current_y_coordinate = 0;
    int y_to_be_located = 0;
    for (int i = 1; i < number_of_vertices + 1; i++) {
      current_x_coordinate = vertex_x_coordinate[i];
      current_y_coordinate = vertex_y_coordinate[i];
      if (current_x_coordinate > 1) {
        x_to_be_located = current_x_coordinate - 1;
        y_to_be_located = current_y_coordinate;
        int destination = check_for_point(x_to_be_located, y_to_be_located, vertex_x_coordinate, vertex_y_coordinate);
        {
          if (destination > 0 && destination != i)
            adjacency_matrix[i][destination] = 1;
        }
      }
      if (current_x_coordinate < size) {
        x_to_be_located = current_x_coordinate + 1;
        y_to_be_located = current_y_coordinate;
        int destination = check_for_point(x_to_be_located, y_to_be_located, vertex_x_coordinate, vertex_y_coordinate);
        {
          if (destination > 0 && destination != i)
            adjacency_matrix[i][destination] = 1;
        }
      }
      if (current_y_coordinate > 1) {
        y_to_be_located = current_y_coordinate - 1;
        x_to_be_located = current_x_coordinate;
        int destination = check_for_point(x_to_be_located, y_to_be_located, vertex_x_coordinate, vertex_y_coordinate);
        {
          if (destination > 0)
            adjacency_matrix[i][destination] = 1;
        }
      }
      if (current_y_coordinate < size) {
        y_to_be_located = current_y_coordinate + 1;
        x_to_be_located = current_x_coordinate;
        int destination = check_for_point(x_to_be_located, y_to_be_located, vertex_x_coordinate, vertex_y_coordinate);
        {
          if (destination > 0)
            adjacency_matrix[i][destination] = 1;
        }
      }
    }

    System.out.println("Enter the coordinates of the source. ");
    System.out.println("Note that the source and end points must be free path squares.");
    System.out.println("Enter the x coordinate of the source");
    int x_coordinate = in.nextInt();
    System.out.println("Enter the y coordinate of the source");
    int y_coordinate = in.nextInt();
    int source = check_for_point(x_coordinate, y_coordinate, vertex_x_coordinate, vertex_y_coordinate);
    System.out.println("The source vertex is " + source);

    int distances[] = new int[number_of_vertices + 1];
    int predecessor[] = new int[number_of_vertices + 1];

    for (int node = 1; node <= number_of_vertices; node++) {
      distances[node] = MAX_VALUE;
    }
    distances[source] = 0;

    for (int node = 1; node <= number_of_vertices - 1; node++) {
      for (int sourcenode = 1; sourcenode <= number_of_vertices; sourcenode++) {
        for (int destinationnode = 1; destinationnode <= number_of_vertices; destinationnode++)

        {
          if (adjacency_matrix[sourcenode][destinationnode] != MAX_VALUE) // if there is a path from source node to
                                                                          // destination node
          {
            if (distances[destinationnode] > distances[sourcenode] + adjacency_matrix[sourcenode][destinationnode]) {
              distances[destinationnode] = distances[sourcenode] + adjacency_matrix[sourcenode][destinationnode];
              predecessor[destinationnode] = sourcenode;
            }
          }

          // new distance of destination from source is distance of sourcenode from source
          // added to the distance between the sourcenode and destination node
        }
      }
    }

    System.out.println("Name the coordinates of the end point of the maze");
    System.out.println("Enter the x coordinate of the end point");
    x_coordinate = in.nextInt();
    System.out.println("Enter the y coordinate of the end point ");
    y_coordinate = in.nextInt();

    int i = check_for_point(x_coordinate, y_coordinate, vertex_x_coordinate, vertex_y_coordinate);

    int path_followed[] = new int[number_of_vertices + 1];
    String directions[] = new String[number_of_vertices];

    int pre = i;
    int j = 1;
    path_followed[0] = i;
    {
      while (j < number_of_vertices + 1) {
        path_followed[j] = predecessor[pre];
        pre = predecessor[pre];
        j++;

      }

    }
    int final_maze_solved[][] = new int[size + 1][size + 1];
    int initial_x_coordinate = vertex_x_coordinate[source];
    int initial_y_coordinate = vertex_y_coordinate[source];
    int counter = 0;

    for (int k = number_of_vertices - 1; k >= 0; k--) {
      if (path_followed[k] != 0) {
        int x_distance = vertex_x_coordinate[path_followed[k]] - initial_x_coordinate;
        int y_distance = vertex_y_coordinate[path_followed[k]] - initial_y_coordinate;
        initial_x_coordinate = vertex_x_coordinate[path_followed[k]];
        initial_y_coordinate = vertex_y_coordinate[path_followed[k]];

        /*
         * System.out.println("x_distance is " + x_distance);
         * System.out.println("y_distance is " + y_distance);
         */
        if (x_distance > 0) {
          directions[counter] = "Right";
        }
        if (x_distance < 0) {
          directions[counter] = "Left";
        }
        if (y_distance > 0) {
          directions[counter] = "Down";
        }
        if (y_distance < 0) {
          directions[counter] = "Up";
        }
        counter++;

        System.out.println(
            "Point " + vertex_x_coordinate[path_followed[k]] + "," + vertex_y_coordinate[path_followed[k]] + " then ");
        final_maze_solved[vertex_y_coordinate[path_followed[k]]][vertex_x_coordinate[path_followed[k]]] = 1;
      }

    }
    System.out.println(
        "move to point " + vertex_x_coordinate[i] + "," + vertex_y_coordinate[i] + " to reach your destination");
    final_maze_solved[vertex_y_coordinate[i]][vertex_x_coordinate[i]] = 1;
    System.out.println("The path, as indicated by the marking 'O'");
    for (int k = 1; k < size + 1; k++) {
      for (int l = 1; l < size + 1; l++) {
        if (final_maze_solved[k][l] == 1)
          System.out.print(" O ");

        else
          System.out.print("   ");
      }
      System.out.println();
    }
    for (int k = 0; k < number_of_vertices; k++) {
      if (directions[k] != null)
        System.out.println("Move 1 block " + directions[k]);
    }
    int final_angle = 0;
    int right = 90;
    int angle_one = 0;
    int angle_two = 0;
    int left = -90;
    int up = 0;
    int down = 180;
    int counter_for_angle_array = 0;
    int angle_array[] = new int[number_of_vertices - 1];

    for (int k = 0; k < number_of_vertices; k++) {
      if (directions[k] != null) {

        if (directions[k] == "Right") {
          angle_two = right;
        }
        if (directions[k] == "Left") {
          angle_two = left;
        }
        if (directions[k] == "Up") {
          angle_two = up;
        }
        if (directions[k] == "Down") {
          angle_two = down;
        }
        if (directions[k - 1] == "Right") {
          angle_one = right;
        }
        if (k > 0)

        {
          if (directions[k - 1] == "Left")
            angle_one = left;

          if (directions[k - 1] == "Up")
            angle_one = up;

          if (directions[k - 1] == "Down")
            angle_one = down;
        }

        final_angle = angle_two - angle_one;
        angle_array[counter_for_angle_array] = final_angle;
        counter_for_angle_array++;

        System.out.print("Robot turns by " + Math.abs(final_angle) + " DEGREES ");

        if (final_angle < 0) {
          System.out.println(" to the LEFT");
          System.out.println("The right motor moves forward for (seconds for one degree) multiplied by " + final_angle);
          System.out.println("The left motor moves backward for (seconds for one degree) multiplied by " + final_angle);
        }
        if (final_angle > 0) {
          System.out.println(" to the RIGHT");
          System.out.println("The left motor moves forward for (seconds for one degree multiplied by " + final_angle);
          System.out.println("The right motor moves backward for (seconds for one degree multiplied by " + final_angle);
        }
        if (final_angle == 0) {
          System.out.println(" (The robot does not turn.)");
        }

      }
    }
    return (angle_array);
  }

  int check_for_point(int x_to_be_located, int y_to_be_located, int vertex_x_coordinate[], int vertex_y_coordinate[]) {
    int final_position = -1;
    int position = -1;
    {
      for (int i = 1; i < vertex_x_coordinate.length; i++) {
        if (vertex_x_coordinate[i] == x_to_be_located) {
          position = i;
          if (vertex_y_coordinate[position] == y_to_be_located) {
            final_position = i;
            break;
          }
        }

      }
      return (final_position);
    }
  }// end of method check_for_point

  public static void main_LINE_FOLLOWER() {
    Scanner in = new Scanner(System.in);
    new_bellman_ford bell = new new_bellman_ford();
    int angle_array[] = bell.input();
    int counter_for_turns = 0;
    String response = "";

    while (true) {
      System.out.println("Do both sensors see the black line?");
      response = in.nextLine();
      if (response.equals("Yes")) {
        int final_angle = angle_array[counter_for_turns];
        counter_for_turns++;

        System.out.print("Robot turns by " + Math.abs(final_angle) + " DEGREES ");

        if (final_angle < 0) {
          System.out.println(" to the LEFT");
          System.out.println("The right motor moves forward for (seconds for one degree) multiplied by " + final_angle);
          System.out.println("The left motor moves backward for (seconds for one degree) multiplied by " + final_angle);
        }
        if (final_angle > 0) {
          System.out.println(" to the RIGHT");
          System.out.println("The left motor moves forward for (seconds for one degree multiplied by " + final_angle);
          System.out.println("The right motor moves backward for (seconds for one degree multiplied by " + final_angle);
        }
        if (final_angle == 0) {
          System.out.println(" (The robot does not turn.)");
        }
      }
      if (response.equals("no")) {
        System.out.println("Following the line as per the line follower code of Arduino");
      }
      if (counter_for_turns == angle_array.length) {
        System.out.println("Robot has reached destination. Program terminates.");
        break;
      }
    }

    for (int i = 0; i < angle_array.length; i++) {
      System.out.println(angle_array[i]);
    }

  }

}
