# Create: Electric Pump

[![Build Mod](https://github.com/Steam2208/Create_Electric_pump/actions/workflows/build.yml/badge.svg)](https://github.com/Steam2208/Create_Electric_pump/actions/workflows/build.yml)
[![License: Unlicense](https://img.shields.io/badge/license-Unlicense-blue.svg)](http://unlicense.org/)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-brightgreen.svg)](https://minecraft.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-21.1.247-orange.svg)](https://neoforged.net/)
[![Create](https://img.shields.io/badge/Create-1.21.1--6.0.10-copper.svg)](https://modrinth.com/mod/create)

**Create: Electric Pump** — аддон для модификации [Create](https://github.com/Creators-of-Create/Create) под **Minecraft 1.21.1 (NeoForge)**, добавляющий **Электрическую помпу**, работающую на энергии FE (Forge / NeoForge Energy) вместо кинетического вращения (SU).

---

## ⚡ Особенности / Features

- 🔋 **Энергия вместо вращения:**
  - Потребление: **64 FE/t**
  - Буфер: **10 000 FE** (макс. вход: 1 000 FE/t)
  - Виртуальная скорость: **128 об/мин (RPM)**
  - Не требует подключения валов, шестерёнок и не тратит стресс (SU) кинетической сети!
- 🔌 **Точное подключение:**
  - Энергия подключается строго через выступающий электрический разъём на помпе.
- 🔧 **Умное управление гаечным ключом Create (Wrench):**
  - **Горизонтальное положение:** разъём энергии всегда находится сверху. Клик ключом Create в любую точку помпы разворачивает направление перекачки (вход $\leftrightarrow$ выход).
  - **Вертикальное положение:**
    - Клик ключом по торцу (входу/выходу — верхней или нижней открытой грани) поворачивает помпу по кругу (Север $\rightarrow$ Восток $\rightarrow$ Юг $\rightarrow$ Запад), направляя разъём энергии в нужную сторону.
    - Клик ключом по боковым граням разворачивает поток (вверх $\leftrightarrow$ вниз).
- 🥽 **Поддержка Очков Инженера (Engineer's Goggles):**
  - При взгляде на помпу в очках отображается точный запас FE, текущее потребление и статус работы.
- 🌐 **Локализация:**
  - Полный перевод на русский (`ru_ru`) и английский (`en_us`).

---

## 🛠️ Крафт / Crafting Recipe

Крафт в верстаке (бесформенный / shapeless):
- ⚙️ **Механическая помпа** (`create:mechanical_pump`)
- 💡 **Электронная лампа** (`create:electron_tube`)
- ⏱️ **Механизм точности** (`create:precision_mechanism`)

---

## 📦 Требования / Requirements

- **Minecraft:** 1.21.1
- **NeoForge:** 21.1.247+
- **Create:** 1.21.1-6.0.10+

---

## 💻 Сборка из исходников / Building from Source

```bash
git clone https://github.com/Steam2208/Create_Electric_pump.git
cd Create_Electric_pump
./gradlew build
```
Собранный `.jar` появится в папке `build/libs/`.

---

## 📄 Лицензия / License

Проект распространяется под общественной лицензией [The Unlicense](LICENSE).
