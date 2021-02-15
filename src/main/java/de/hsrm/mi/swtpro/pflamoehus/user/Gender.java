package de.hsrm.mi.swtpro.pflamoehus.user;

public enum Gender {
     /**
         * type 'männlich'
         */
        MALE("MALE"),
        /**
         * type 'weiblich'
         */
        FEMALE("FEMALE"),
        /**
         * type 'diverse'
         */
        DIVERSE("DIVERSE");

        private String value;

        Gender(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
}
