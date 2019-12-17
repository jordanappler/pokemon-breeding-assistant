<!--
Copyright 2019 Jordan Appler

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

# Pokémon Breeding Assistant
Helps Pokémon breeders efficiently pass down perfect IVs

## Approach
It is sometimes difficult to choose which Pokémon to breed or which held items to use.
This tool helps breeders make such decisions by comparing the effectiveness of different options.
Based on the perfect IVs and held items of the parents and the breeding mechanics below, a probability tree is generated
and used to evaluate the likelihood that a hatched Pokémon meets the breeder's perfect IV criteria.

## Breeding Mechanics
By default, Pokémon inherit three IVs from their parents at random. The remaining three IVs are randomly generated.

### Held Items
Certain items, if held by a parent, affect the how IVs are passed down.
Note that use of two Power Items together is unreliable. This tool assumes that a second Power Item has no effect.

| Item         | Effects                              |
| ------------ | ------------------------------------ |
| Destiny Knot | Inherits five IVs instead of three   |
| Power Weight | Inherits holder's HP IV              |
| Power Bracer | Inherits holder's Attack IV          |
| Power Belt   | Inherits holder's Defense IV         |
| Power Lens   | Inherits holder's Special Attack IV  |
| Power Band   | Inherits holder's Special Defense IV |
| Power Anklet | Inherits holder's Speed IV           |

## Build
Linux
```bash
./gradlew build
```
Windows
```bash
gradlew.bat build
```

## Run
Linux
```bash
java -jar build/libs/pokemon-breeding-assistant-0.1.jar
```
Windows
```bash
java -jar build\libs\pokemon-breeding-assistant-0.1.jar
```
