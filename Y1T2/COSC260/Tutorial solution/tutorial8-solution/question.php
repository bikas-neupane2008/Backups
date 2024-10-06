<?php

class Question implements JsonSerializable {

    public $id;
    public $text;
    public $choices;
    public $answer;

    public function __construct($id, $text, $choices, $answer) {
        $this->id = $id;
        $this->text = $text;
        $this->answer = $answer;

        // Instead of a 'flat' array, convert the possible answers into
        // an associative array with "A", "B", "C", or "D" as associated keys
        for ($i=0, $c="A"; $i<count($choices); $i++, $c++) {
			$this->choices[$c] = $choices[$i];
		}
    }

    public function checkAnswer($s) {
        return $this->answer === $s;
    }

    public function jsonSerialize() {
        // This function converts a Question object into an Array to be handled by json_encode()
		return [
        	'id' => $this->id,
        	'text' => $this->text,
        	'choices' => $this->choices
		];
	}
}


$q = new Question(12345,
    "What is the largest animal to ever exist on earth?",
    ["Woolly Mammoth","African elephant","T-Rex","Blue Whale"],
    "D"
);

if ($q->checkAnswer("A")){
    print("Answer is A");

} elseif ($q->checkAnswer("B")){
    print("Answer is B");

} elseif ($q->checkAnswer("C")){
    print("Answer is C");

} elseif ($q->checkAnswer("D")){
    print("Answer is D");
}


?>
