# Create: Electric Pump

A lightweight addon for the Create mod (Minecraft 1.21.1, NeoForge) that adds an electric pump. It pumps fluids through Create pipe networks using FE energy, without requiring kinetic rotational shafts or stress units (SU).

## Features

- **Power:** Consumes 64 FE/t while operating (10,000 FE internal buffer).
- **Performance:** Generates fluid pressure equivalent to a mechanical pump running at 128 RPM.
- **Connection:** Power is accepted strictly through the protruding connector on the casing.
- **Wrench Controls (Create Wrench):**
  - Horizontal orientation: The power connector always faces up. Right-clicking with a wrench flips the flow direction.
  - Vertical orientation: Right-clicking the pipe ends (inlet or outlet) rotates the pump in a circle, allowing you to aim the power connector in any direction. Right-clicking the side faces flips the flow direction (up/down).
- **Engineer's Goggles:** Displays stored energy, consumption rate, and operating status when looked at.

## Crafting Recipe

Shapeless crafting table recipe:
- Mechanical Pump (`create:mechanical_pump`)
- Electron Tube (`create:electron_tube`)
- Precision Mechanism (`create:precision_mechanism`)

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.247+
- Create 1.21.1-6.0.10+

## License

[The Unlicense](LICENSE)
