"""Regenerate the original pixel wing textures using only Python's standard library."""
from pathlib import Path
import struct
import zlib

ROOT = Path(__file__).resolve().parents[1] / 'common/src/main/resources/assets/flying_creeper'
CLEAR = (0, 0, 0, 0)
BORDER = (42, 81, 35, 255)
MEMBRANE = (177, 215, 148, 255)
VEIN = (85, 140, 58, 255)


class Texture:
    def __init__(self, width, height, background=CLEAR):
        self.width, self.height = width, height
        self.pixels = [[background for _ in range(width)] for _ in range(height)]

    def rectangle(self, x0, y0, x1, y1, color):
        for y in range(max(0, y0), min(self.height, y1 + 1)):
            for x in range(max(0, x0), min(self.width, x1 + 1)):
                self.pixels[y][x] = color

    def membrane(self, x0, y0, x1, y1):
        self.rectangle(x0, y0, x1, y1, BORDER)
        self.rectangle(x0 + 1, y0 + 1, x1 - 1, y1 - 1, MEMBRANE)
        for y in range(y0 + 2, y1, 4):
            for x in range(x0 + 1, x1):
                self.rectangle(x, max(y0 + 1, y - (x - x0) // 3), x,
                               max(y0 + 1, y - (x - x0) // 3), VEIN)

    def save(self, relative):
        def chunk(kind, data):
            return struct.pack('>I', len(data)) + kind + data + struct.pack('>I', zlib.crc32(kind + data))
        raw = b''.join(b'\0' + bytes(c for pixel in row for c in pixel) for row in self.pixels)
        png = b'\x89PNG\r\n\x1a\n' + chunk(b'IHDR', struct.pack('>IIBBBBB', self.width, self.height, 8, 6, 0, 0, 0))
        png += chunk(b'IDAT', zlib.compress(raw)) + chunk(b'IEND', b'')
        path = ROOT / relative
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(png)


def main():
    entity = Texture(32, 32)
    for box in [(1, 0, 10, 0), (11, 0, 20, 0), (0, 1, 0, 12),
                (1, 1, 10, 12), (11, 1, 11, 12), (12, 1, 21, 12)]:
        entity.membrane(*box)
    entity.save('textures/entity/creeper_wings.png')
    player = Texture(64, 32)
    for box in [(22, 0, 31, 1), (32, 0, 41, 1), (20, 2, 21, 21),
                (22, 2, 31, 21), (32, 2, 33, 21), (34, 2, 43, 21)]:
        player.membrane(*box)
    player.save('textures/entity/equipment/wings/creeper_wings.png')
    for broken in (False, True):
        item = Texture(16, 16)
        for y in range(1, 14):
            inset = abs(y - 5) // 3
            item.membrane(1 + inset, y, 6, y)
            item.membrane(9, y, 14 - inset, y)
        item.rectangle(7, 3, 8, 10, VEIN)
        for x in range(2, 6):
            item.rectangle(x, 3, x, 10, MEMBRANE if x % 2 else VEIN)
            item.rectangle(15 - x, 3, 15 - x, 10, MEMBRANE if x % 2 else VEIN)
        if broken:
            for x in range(1, 7):
                item.rectangle(x, 6 + x // 2, x, 7 + x // 2, CLEAR)
                item.rectangle(15 - x, 5 + x // 2, 15 - x, 6 + x // 2, CLEAR)
        item.save('textures/item/creeper_wings' + ('_broken' if broken else '') + '.png')
    icon = Texture(64, 64, (28, 44, 26, 255))
    icon.membrane(4, 12, 23, 49)
    icon.membrane(40, 12, 59, 49)
    icon.rectangle(22, 17, 41, 40, (89, 158, 53, 255))
    for box in [(25, 23, 29, 27), (34, 23, 38, 27), (29, 28, 34, 36), (26, 32, 37, 37)]:
        icon.rectangle(*box, (0, 0, 0, 255))
    icon.save('icon.png')


if __name__ == '__main__':
    main()
