<?php

class AllGeeks implements JsonSerializable {
    private $username;
    private $fullName;
    private $email;
    private $dateOfBirth;

    public function __construct($username, $fullName, $email, $dateOfBirth) {
        $this->username = $username;
        $this->fullName = $fullName;
        $this->email = $email;
        $this->dateOfBirth = $dateOfBirth;
    }

    public function jsonSerialize() {
        return [
            'username' => $this->username,
            'fullName' => $this->fullName,
            'email' => $this->email,
            'dateOfBirth' => $this->dateOfBirth
        ];
    }
}
?>
