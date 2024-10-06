<?php

class Point3D {

    public $x;
    public $y;
    public $z;

    public function __construct($x, $y, $z){
        $this->x = $x;
        $this->y = $y;
        $this->z = $z;
    }

    public function getLength(){
        // Length of a vector using Pythagorean theorem: sqrt(a^2 + b^2 + c^2)
        return sqrt(
            pow($this->x, 2) +
            pow($this->y, 2) +
            pow($this->z, 2));
    }

}

$p1 = new Point3D(1, 2, 3);
$p2 = new Point3D(7, 8, 9);
$p3 = new Point3D(10, 20, 30);

print($p1->getLength() . "\n");
print($p2->getLength() . "\n");
print($p3->getLength() . "\n");

?>
