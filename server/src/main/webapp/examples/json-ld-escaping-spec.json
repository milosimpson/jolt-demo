[
  {
    "operation": "shift",
    "spec": {
      // In this example, we want to change some of the LHS keys in this Json document.
      // To accomplish this we escape the leading '@' chars both as spec matches (left hand side)
      //  and as output paths (right hand side).
      //
      // Note in order to escape Shiftr special chars, you have to use two backslashes,
      //  because Java.
      // Also, all other Shiftr special chars should be escapable: . @ $ & \ [  ]
      "\\@context": {
        "name": "&1.Name",
        "ingredient": "&1.Inputs",
        "yield": "\\@context.Makes",
        // pass the rest thru
        "*": "&1.&"
      },
      "name": "Name",
      "ingredient": "Inputs",
      "yield": "Makes",
      "*": "&"
    }
  }
]