[
  {
    "operation": "shift",
    "spec": {
      "*": {
        "${name}*": "@(0)"
      }
    }
  },
  {
    "operation": "shift",
    "spec": {
      "*": "${name}s"
    }
  },
  {
    "operation": "modify-overwrite-beta",
    "spec": {
      "sumIntData": "=intSum(@(1,items))"
    }
  }
]